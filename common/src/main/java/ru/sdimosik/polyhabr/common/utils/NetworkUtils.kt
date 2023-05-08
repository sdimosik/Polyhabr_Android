package ru.sdimosik.polyhabr.common.utils

import retrofit2.HttpException
import retrofit2.Response

object NetworkUtils {
    fun <T> unwrap(response: Response<T>): T {
        if (response.isSuccessful) {
            return response.body().takeIf { it != null }
                ?: throw RuntimeException("Проблема получения данных")
        } else {
            throw HttpException(response)
        }
    }
}