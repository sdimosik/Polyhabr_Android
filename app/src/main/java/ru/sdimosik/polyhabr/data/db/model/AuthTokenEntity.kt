package ru.sdimosik.polyhabr.data.db.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthTokenEntity(
    var accessToken: String,
    var username: String,
    val isFirst: Boolean,
    val refreshToken: String,
    val idUser: Long,
    val password: String
) : Parcelable
