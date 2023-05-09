package ru.sdimosik.polyhabr.data.network.model.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleType(
    var name: String? = null,
) : Parcelable