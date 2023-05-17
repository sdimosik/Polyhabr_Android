package ru.sdimosik.polyhabr.presentaion.main.profile.reg.confirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.model.user.LoginRequest
import ru.sdimosik.polyhabr.data.network.model.user.NewUser
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import ru.sdimosik.polyhabr.utils.ErrorHelper
import ru.sdimosik.polyhabr.utils.loading
import javax.inject.Inject

@HiltViewModel
class ConfirmRegViewModel @Inject constructor(
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

    private val _goMyProfile = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val goMyProfile = _goMyProfile.asSharedFlow()

    var verifyCode = ""

    fun verify(newUser: NewUser) {
        val loginRequest = LoginRequest(
            username = newUser.username,
            password = newUser.password
        )
        authInteractor.verifyUser(verifyCode)
            .andThen(authInteractor.login(loginRequest))
            .loading<Boolean>(_isLoading)
            .subscribeBy(
                onComplete = {
                    _goMyProfile.tryEmit(true)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }
}