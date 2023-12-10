package ru.sdimosik.polyhabr.domain.interactor

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.sdimosik.polyhabr.common.utils.NetworkUtils
import ru.sdimosik.polyhabr.data.db.IAuthStorage
import ru.sdimosik.polyhabr.data.db.model.AuthTokenEntity
import ru.sdimosik.polyhabr.data.network.RefreshApi
import ru.sdimosik.polyhabr.data.network.model.user.*
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import java.util.Optional
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val networkRepository: INetworkRepository,
    private val refreshApi: RefreshApi,
    private val authStorage: IAuthStorage
) : IAuthInteractor {
    override fun isAuthorized(): Single<Boolean> {
        return Single.just(authStorage.getToken() != null)
    }

    override fun getSavedToken(): Single<Optional<AuthTokenEntity>> {
        return Single.just(Optional.ofNullable(authStorage.getToken()))
    }

    override fun deleteToken(): Completable {
        return authStorage.deleteToken()
    }

    override fun storeToken(authTokenEntity: AuthTokenEntity): Completable {
        return authStorage.saveToken(authTokenEntity)
    }

    override fun login(loginRequest: LoginRequest): Completable {
        return networkRepository.login(loginRequest)
            .flatMapCompletable { authStorage.saveToken(it.toEntity(loginRequest.password)) }
    }

    override fun register(newUser: NewUser): Completable {
        return networkRepository.register(newUser)
    }

    override fun checkFreeLogin(login: String): Completable {
        return networkRepository.checkFreeLogin(login)
    }

    override fun checkFreeEmail(email: String): Completable {
        return networkRepository.checkFreeEmail(email)
    }

    override fun refresh(refreshRequest: TokenRefreshRequest): Single<TokenRefreshResponse> {
        return refreshApi.refreshTokenResponse(refreshRequest)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun verifyUser(code: String): Completable {
        return networkRepository.verifyUser(code)
    }

    override fun updateUser(userUpdateRequest: UserUpdateRequest): Completable {
        return networkRepository.updateUser(userUpdateRequest)
    }

    override fun meUser(): Single<UserMeResponse> {
        return networkRepository.meUser()
    }
}

interface IAuthInteractor {

    fun isAuthorized(): Single<Boolean>

    fun getSavedToken(): Single<Optional<AuthTokenEntity>>

    fun deleteToken(): Completable

    fun storeToken(authTokenEntity: AuthTokenEntity): Completable

    fun login(loginRequest: LoginRequest): Completable

    fun register(newUser: NewUser): Completable

    fun checkFreeLogin(login: String): Completable

    fun checkFreeEmail(email: String): Completable

    fun refresh(refreshRequest: TokenRefreshRequest): Single<TokenRefreshResponse>

    fun verifyUser(code: String): Completable

    fun updateUser(userUpdateRequest: UserUpdateRequest): Completable

    fun meUser(): Single<UserMeResponse>

}
