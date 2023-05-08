package ru.sdimosik.polyhabr.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sdimosik.polyhabr.data.repository_impl.NetworkRepository
import ru.sdimosik.polyhabr.domain.interactor.ArticlesInteractor
import ru.sdimosik.polyhabr.domain.interactor.IArticlesInteractor
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object {
        @Provides
        @Singleton
        fun provideContext(app: Application): Context = app.applicationContext
    }

    @Binds
    fun bindNetworkRepository(
        networkRepositoryImpl: NetworkRepository
    ): INetworkRepository

    @Binds
    fun bindArticlesInteractor(
        articlesInteractorImpl: ArticlesInteractor
    ): IArticlesInteractor
}