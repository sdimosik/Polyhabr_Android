package ru.sdimosik.polyhabr.utils

import android.content.Context
import retrofit2.HttpException
import javax.inject.Inject

object ErrorHelper {
    fun getErrorMessage(throwable: Throwable): String {
        return if (throwable is HttpException) {
            val code = throwable.code()
            when (code) {
                401 -> "Сначала авторизируйтесь"
                404 -> "Страница не найдена"
                in 400..499 -> "Ошибка приложения"
                in 500..599 -> "Ошибка сервера"
                else -> "Неизвестная ошибка"
            }
        } else {
            "Неполадки :("
        }
    }
}