package ru.sdimosik.polyhabr.data.db

import io.reactivex.rxjava3.core.Completable
import ru.sdimosik.polyhabr.data.db.model.AuthTokenEntity

interface IAuthStorage {
    fun saveToken(token: AuthTokenEntity): Completable
    fun getToken(): AuthTokenEntity?
    fun deleteToken(): Completable
}
