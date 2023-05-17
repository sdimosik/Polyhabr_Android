package ru.sdimosik.polyhabr.presentaion.main.profile.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.model.user.UserMeResponse
import ru.sdimosik.polyhabr.data.network.model.user.UserUpdateRequest
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import ru.sdimosik.polyhabr.utils.ErrorHelper
import ru.sdimosik.polyhabr.utils.RegexUtils
import ru.sdimosik.polyhabr.utils.loading
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
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

    private val _goBaseAuth = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val goBaseAuth = _goBaseAuth.asSharedFlow()

    private val _goBack = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val goBack = _goBack.asSharedFlow()

    var oldPass = ""
    var newPass = ""
    var retryPass = ""
    var firstName = ""
    var lastName = ""
    var email = ""

    private val _meUser = MutableLiveData<UserMeResponse>()
    val meUser: LiveData<UserMeResponse> = _meUser

    init {
        loadMeProfile()
    }

    fun loadMeProfile() {
        authInteractor.meUser()
            .subscribeBy(
                onSuccess = {
                    _meUser.postValue(it)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun exitFromAcc() {
        authInteractor.deleteToken()
            .subscribeBy(
                onComplete = {
                    _goBaseAuth.tryEmit(Unit)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun updateInfo() {
        val oldPassTmp = oldPass.ifEmpty { null }
        val newPassTmp = newPass.ifEmpty { null }
        val retryPassTmp = retryPass.ifEmpty { null }
        val firstNameTmp =
            if (firstName == meUser.value?.name || firstName.isEmpty()) null else firstName
        val lastNameTmp =
            if (lastName == meUser.value?.surname || lastName.isEmpty()) null else lastName
        val emailTmp = if (email == meUser.value?.email || email.isEmpty()) null else email

        val oldPassValid = oldPassTmp?.let { RegexUtils.validatePassword(it) } ?: (true to "")
        val newPassValid = newPassTmp?.let { RegexUtils.validatePassword(it) } ?: (true to "")
        val retryPassValid = newPassTmp?.let { RegexUtils.validatePassword(it) } ?: (true to "")

        if (!oldPassValid.first) {
            _error.tryEmit(oldPassValid.second)
            return
        }
        if (!newPassValid.first) {
            _error.tryEmit(newPassValid.second)
            return
        }
        if (!retryPassValid.first) {
            _error.tryEmit(retryPassValid.second)
            return
        }

        val firstNameValid = firstNameTmp?.let { RegexUtils.validateName(it) } ?: (true to "")
        val lastNameValid = lastNameTmp?.let { RegexUtils.validateName(it) } ?: (true to "")

        if (!firstNameValid.first) {
            _error.tryEmit(firstNameValid.second)
            return
        }
        if (!lastNameValid.first) {
            _error.tryEmit(lastNameValid.second)
            return
        }

        val emailValid = emailTmp?.let { RegexUtils.validateEmail(it) } ?: (true to "")
        if (!emailValid.first) {
            _error.tryEmit(emailValid.second)
            return
        }

        authInteractor.getSavedToken()
            .flatMap {
                if (it.isPresent) {
                    val currentPass = it.get().password
                    if (currentPass != oldPassTmp && newPassTmp != retryPassTmp) {
                        error("Ошибка в паролях")
                    } else {
                        Single.just("")
                    }
                } else {
                    Single.just("")
                }
            }
            .flatMapCompletable {
                if (!emailTmp.isNullOrEmpty() && emailTmp != _meUser.value?.email) {
                    authInteractor.checkFreeEmail(emailTmp)
                } else {
                    Completable.complete()
                }
            }
            .andThen(
                authInteractor.updateUser(
                    UserUpdateRequest(
                        email = emailTmp,
                        name = firstNameTmp,
                        surname = lastNameTmp,
                        password = oldPassTmp,
                        newPassword = newPassTmp
                    )
                )
            )
            .loading<Boolean>(_isLoading)
            .subscribeBy(
                onComplete = {
                    _goBack.tryEmit(Unit)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }
}