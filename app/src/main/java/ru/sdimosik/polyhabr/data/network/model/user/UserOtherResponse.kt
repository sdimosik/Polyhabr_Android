package ru.sdimosik.polyhabr.data.network.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserOtherResponse(
    val id: Long,
    val login: String,
    val name: String,
    val surname: String,
) : Parcelable