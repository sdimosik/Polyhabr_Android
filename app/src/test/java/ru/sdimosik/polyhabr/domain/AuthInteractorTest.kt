package ru.sdimosik.polyhabr.domain

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.sdimosik.polyhabr.data.db.IAuthStorage
import ru.sdimosik.polyhabr.data.db.model.AuthTokenEntity
import ru.sdimosik.polyhabr.data.network.RefreshApi
import ru.sdimosik.polyhabr.data.network.model.user.LoginRequest
import ru.sdimosik.polyhabr.data.network.model.user.LoginResponse
import ru.sdimosik.polyhabr.data.network.model.user.NewUser
import ru.sdimosik.polyhabr.domain.interactor.ArticlesInteractor
import ru.sdimosik.polyhabr.domain.interactor.AuthInteractor
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository

class AuthInteractorTest {

    private val networkRepository: INetworkRepository = mock()
    private val refreshApi: RefreshApi = mock()
    private val authStorage: IAuthStorage = mock()
    private lateinit var authInteractor: IAuthInteractor

    private companion object {
        @BeforeAll
        @JvmStatic
        fun setupSchedulers() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @BeforeEach
    fun beforeEach() {
        authInteractor = AuthInteractor(networkRepository, refreshApi, authStorage)
    }

    @Test
    fun `isAuthorized should return false if default`() {
        whenever(authStorage.getToken()).thenReturn(null)
        val result = authInteractor.isAuthorized()
        result.test().assertValue(false)
    }

    @Test
    fun `getSavedToken should return empty if default`() {
        val authTokenEntity = mock<AuthTokenEntity>()
        val result = authInteractor.getSavedToken()

        whenever(authStorage.getToken()).thenReturn(authTokenEntity)

        result.test().assertValueCount(1)
    }

    @Test
    fun `deleteToken should return Completable`() {
        whenever(authStorage.deleteToken()).thenReturn(Completable.complete())
        val result = authInteractor.deleteToken()
        result.test().assertComplete()
    }

    @Test
    fun `storeToken should return Completable`() {
        val authTokenEntity = mock<AuthTokenEntity>()
        whenever(authStorage.saveToken(authTokenEntity)).thenReturn(Completable.complete())
        val result = authInteractor.storeToken(authTokenEntity)
        result.test().assertComplete()
    }

    @Test
    fun `login should return Completable`() {
        val loginRequest = LoginRequest(
            username = "username",
            password = "password"
        )
        val loginResponse = LoginResponse(
            accessToken = "accessToken",
            username = "username",
            isFirst = true,
            refreshToken = "refreshToken",
            idUser = 1
        )
        whenever(networkRepository.login(any())).thenReturn(Single.just(loginResponse))
        whenever(authStorage.saveToken(any())).thenReturn(Completable.complete())
        val result = authInteractor.login(loginRequest)
        result.test().assertComplete()
    }

    @Test
    fun `register should return Completable`() {
        val newUser = mock<NewUser>()
        whenever(networkRepository.register(newUser)).thenReturn(Completable.complete())
        val result = authInteractor.register(newUser)
        result.test().assertComplete()
    }
}