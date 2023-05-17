package ru.sdimosik.polyhabr.presentaion.main.profile.reg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.model.user.NewUser
import ru.sdimosik.polyhabr.data.network.model.user.validate
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import ru.sdimosik.polyhabr.utils.ErrorHelper
import ru.sdimosik.polyhabr.utils.loading
import javax.inject.Inject

@HiltViewModel
class RegViewModel @Inject constructor(
    private val authInteractor: IAuthInteractor
) : BaseViewModel() {

    private val _error = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val error = _error.asSharedFlow()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _goConfirmScreen = MutableSharedFlow<NewUser>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val goConfirmScreen = _goConfirmScreen.asSharedFlow()

    var login = ""
    var password = ""
    var firstName = ""
    var lastName = ""
    var email = ""

    fun register() {
        val param = NewUser(
            username = login,
            password = password,
            firstName = firstName,
            lastName = lastName,
            email = email
        )
        val valid = param.validate()
        if (!valid.first) {
            _error.tryEmit(valid.second)
        } else {
            authInteractor
                .checkFreeLogin(param.username)
                .andThen(authInteractor.checkFreeEmail(param.email))
                .andThen(authInteractor.register(param))
                .loading<Boolean>(_isLoading)
                .subscribeBy(
                    onComplete = {
                        _goConfirmScreen.tryEmit(param)
                    },
                    onError = {
                        _error.tryEmit(ErrorHelper.getErrorMessage(it))
                    }
                )
                .addTo(disposables)
        }
    }
}