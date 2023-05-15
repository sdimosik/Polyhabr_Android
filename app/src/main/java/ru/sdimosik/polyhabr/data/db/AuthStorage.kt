package ru.sdimosik.polyhabr.data.db

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.sdimosik.polyhabr.data.db.model.AuthTokenEntity
import ru.sdimosik.polyhabr.utils.StorageUtils.APP_PREFERENCES_NAME
import ru.sdimosik.polyhabr.utils.editSharedPreferences
import ru.sdimosik.polyhabr.utils.getParcelable
import ru.sdimosik.polyhabr.utils.putParcelable
import javax.inject.Inject

class AuthStorage @Inject constructor(
    private val context: Context
) : IAuthStorage {

    private val preferences = context.getSharedPreferences(
        APP_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
    private val prefSubject = BehaviorSubject.createDefault(preferences)

    private val prefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, _ ->
            prefSubject.onNext(sharedPreferences)
        }

    init {
        preferences.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }


    private companion object {
        const val STORAGE_TOKEN = "STORAGE_TOKEN"
    }

    override fun saveToken(token: AuthTokenEntity): Completable {
        return prefSubject
            .firstOrError()
            .editSharedPreferences {
                putParcelable(STORAGE_TOKEN, token)
            }
    }

    override fun getToken(): AuthTokenEntity? {
        return context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getParcelable(STORAGE_TOKEN, null)
    }

    override fun deleteToken(): Completable {
        return prefSubject
            .firstOrError()
            .editSharedPreferences {
                clear()
            }
    }
}