package ru.sdimosik.polyhabr.data.network

import okhttp3.*
import ru.sdimosik.polyhabr.data.db.IAuthStorage

class AuthTokenHeaderInteractor(
    private val authStorage: IAuthStorage,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authStorage.getToken()?.accessToken.toString()
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}
