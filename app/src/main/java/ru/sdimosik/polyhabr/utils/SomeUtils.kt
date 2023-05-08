package ru.sdimosik.polyhabr.utils

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.MutableStateFlow

// write func where setup loading mutable state flow on rx java android
fun <T : Any> Single<T>.loading(
    loading: MutableStateFlow<Boolean>
): Single<T> = doOnSubscribe { loading.value = true }
    .doFinally { loading.value = false }

fun <T : Any> Completable.loading(
    loading: MutableStateFlow<Boolean>
): Completable = doOnSubscribe { loading.value = true }
    .doFinally { loading.value = false }

fun <T : Any> Single<T>.loading(
    loading: MutableLiveData<Boolean>
): Single<T> = doOnSubscribe { loading.value = true }
    .doFinally { loading.value = false }

fun <T : Any> Completable.loading(
    loading: MutableLiveData<Boolean>
): Completable = doOnSubscribe { loading.value = true }
    .doFinally { loading.value = false }