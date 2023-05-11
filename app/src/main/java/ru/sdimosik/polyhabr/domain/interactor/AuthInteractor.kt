package ru.sdimosik.polyhabr.domain.interactor

import io.reactivex.rxjava3.core.Completable
import ru.sdimosik.polyhabr.data.db.IAuthStorage
import ru.sdimosik.polyhabr.data.network.model.user.LoginRequest
import ru.sdimosik.polyhabr.data.network.model.user.toEntity
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val networkRepository: INetworkRepository,
    private val authStorage: IAuthStorage
) : IAuthInteractor {
    override fun login(loginRequest: LoginRequest): Completable {
        return networkRepository.login(loginRequest)
            .flatMapCompletable { authStorage.saveToken(it.toEntity()) }
    }
}

interface IAuthInteractor {
    fun login(loginRequest: LoginRequest): Completable
}