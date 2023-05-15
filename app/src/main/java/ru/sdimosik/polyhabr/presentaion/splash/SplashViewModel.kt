package ru.sdimosik.polyhabr.presentaion.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.zipWith
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.db.model.AuthTokenEntity
import ru.sdimosik.polyhabr.data.network.model.user.TokenRefreshRequest
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authInteractor: IAuthInteractor
) : BaseViewModel() {
    companion object {
        private const val DELAY = 1000L
    }

    private val _command = MutableStateFlow<SplashActions?>(null)
    val command = _command.asStateFlow()

    init {
        refreshToken()
    }

    fun refreshToken() {
        authInteractor.getSavedToken()
            .flatMap {
                val refreshRequest = TokenRefreshRequest(refreshToken = it.get().refreshToken)
                Single.just(it)
                    .zipWith(authInteractor.refresh(refreshRequest))
            }
            .flatMapCompletable {
                val oldToken = it.first
                val newToken = it.second
                authInteractor.storeToken(
                    oldToken.get().copy(
                        accessToken = newToken.accessToken,
                        refreshToken = newToken.refreshToken
                    )
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _command.value = SplashActions.TO_MAIN
                },
                onError = {
                    clearSavedToken()
                }
            )
            .addTo(disposables)
    }

    fun clearSavedToken() {
        authInteractor.deleteToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _command.value = SplashActions.TO_MAIN
                },
                onError = {
                    _command.value = SplashActions.TO_MAIN
                }
            )
            .addTo(disposables)
    }
}