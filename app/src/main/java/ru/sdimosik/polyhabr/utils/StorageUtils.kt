package ru.sdimosik.polyhabr.utils

import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

object StorageUtils {
    const val APP_PREFERENCES_NAME = "ru.sdimosik.polyhabr.utils.APP_PREFERENCES_NAME"
}

fun SharedPreferences.Editor.putParcelable(key: String, parcelable: Parcelable) {
    val json = Gson().toJson(parcelable)
    putString(key, json)
}

inline fun <reified T : Parcelable?> SharedPreferences.getParcelable(key: String, default: T): T {
    val json = getString(key, null)
    return try {
        if (json != null)
            Gson().fromJson(json, T::class.java)
        else default
    } catch (_: JsonSyntaxException) {
        default
    }
}

fun Single<SharedPreferences>.editSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
    flatMapCompletable {
        Completable.fromAction {
            it.edit().also(batch).apply()
        }
    }

fun Single<SharedPreferences>.clearSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
    flatMapCompletable {
        Completable.fromAction {
            it.edit().also(batch).apply()
        }
    }
