package ru.sdimosik.polyhabr.presentaion.main.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.model.user.LoginRequest
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import ru.sdimosik.polyhabr.utils.ErrorHelper
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: IAuthInteractor
) : BaseViewModel() {

    private val _error = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val error = _error.asSharedFlow()

    var login = ""
    var password = ""

    fun goLogin() {
        val param = LoginRequest(
            username = login,
            password = password
        )
        authInteractor.login(param)
            .subscribeBy(
                onComplete = {

                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }
}