package ru.sdimosik.polyhabr.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseOkHttpClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenOkHttpClient