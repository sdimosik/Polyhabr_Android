package ru.sdimosik.polyhabr.data.network

import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.sdimosik.polyhabr.data.db.IAuthStorage
import ru.sdimosik.polyhabr.data.network.model.user.TokenRefreshRequest
import javax.inject.Inject

class RefreshAuthenticator @Inject constructor(
    private val authStorage: IAuthStorage,
    private val refreshApi: RefreshApi
) : Authenticator {
    private companion object {
        const val RETRY = 2
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        return if (responseCount(response) > RETRY) {
            null
        } else {

            authStorage.getToken()?.refreshToken?.let { oldToken ->
                val param = TokenRefreshRequest(oldToken)

                val res = try {
                    refreshApi.refreshToken(param)
                        .subscribeOn(Schedulers.io())
                        .blockingGet()
                } catch (exception: Exception) {
                    null
                }

                res?.let { tokenResponse ->
                    authStorage.getToken()?.let { oldToken ->
                        val token = oldToken.copy(
                            accessToken = tokenResponse.accessToken,
                            refreshToken = tokenResponse.refreshToken
                        )
                        authStorage.saveToken(token)
                        response.request.newBuilder()
                            .header(
                                "Authorization",
                                "Bearer ${token.accessToken}"
                            )
                            .build()
                    }
                }
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var tmpResponse: Response? = response
        while (tmpResponse?.priorResponse != null) {
            result++
            tmpResponse = tmpResponse.priorResponse
        }
        return result
    }
}