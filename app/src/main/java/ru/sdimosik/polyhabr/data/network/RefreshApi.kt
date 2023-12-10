package ru.sdimosik.polyhabr.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.sdimosik.polyhabr.data.network.model.user.TokenRefreshRequest
import ru.sdimosik.polyhabr.data.network.model.user.TokenRefreshResponse

interface RefreshApi {
    @POST("api/auth/refreshtoken")
    fun refreshToken(@Body refreshRequest: TokenRefreshRequest): Single<TokenRefreshResponse>

    @POST("api/auth/refreshtoken")
    fun refreshTokenResponse(@Body refreshRequest: TokenRefreshRequest): Single<Response<TokenRefreshResponse>>
}
