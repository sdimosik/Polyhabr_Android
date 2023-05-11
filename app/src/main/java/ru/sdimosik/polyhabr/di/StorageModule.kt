package ru.sdimosik.polyhabr.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sdimosik.polyhabr.data.db.AuthStorage
import ru.sdimosik.polyhabr.data.db.IAuthStorage

@Module
@InstallIn(SingletonComponent::class)
interface StorageModule {
    @Binds
    fun bindAuthStorage(authStorage: AuthStorage): IAuthStorage
}