package ru.sdimosik.polyhabr.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File


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

fun getInternalStorageDirectoryPath(context: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        storageManager.primaryStorageVolume.directory!!.absolutePath
    } else {
        Environment.getExternalStorageDirectory().absolutePath
    }
}