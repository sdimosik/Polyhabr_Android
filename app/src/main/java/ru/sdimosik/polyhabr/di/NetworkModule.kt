package ru.sdimosik.polyhabr.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.sdimosik.polyhabr.data.db.IAuthStorage
import ru.sdimosik.polyhabr.data.network.AuthTokenHeaderInteractor
import ru.sdimosik.polyhabr.data.network.NetworkApi
import ru.sdimosik.polyhabr.data.network.RefreshApi
import ru.sdimosik.polyhabr.data.network.RefreshAuthenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private companion object {
        const val BASE_URL = "http://193.124.115.161:8733/"
    }

    @Singleton
    @Provides
    fun provideAuthTokenHeaderInteractor(
        authStorage: IAuthStorage
    ) = AuthTokenHeaderInteractor(authStorage)

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Singleton
    @Provides
    fun provideAuthenticator(
        authStorage: IAuthStorage,
        refreshApi: RefreshApi
    ): RefreshAuthenticator {
        return RefreshAuthenticator(authStorage, refreshApi)
    }

    @Singleton
    @Provides
    @BaseOkHttpClient
    fun provideBaseOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authTokenHeaderInteractor: AuthTokenHeaderInteractor,
        context: Context,
        refreshAuthenticator: RefreshAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(refreshAuthenticator)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(authTokenHeaderInteractor)
            .build()
    }

    @Singleton
    @Provides
    @RefreshTokenOkHttpClient
    fun provideRefreshTokenOkHttpClientt(
        loggingInterceptor: HttpLoggingInterceptor,
        authTokenHeaderInteractor: AuthTokenHeaderInteractor,
        context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(authTokenHeaderInteractor)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkApiService(@BaseOkHttpClient okHttpClient: OkHttpClient): NetworkApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NetworkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRefreshApiService(@RefreshTokenOkHttpClient okHttpClient: OkHttpClient): RefreshApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RefreshApi::class.java)
    }
}
