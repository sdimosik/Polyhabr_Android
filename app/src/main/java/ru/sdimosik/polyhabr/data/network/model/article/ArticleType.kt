package ru.sdimosik.polyhabr.data.network.model.article

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleType(
    @SerializedName("name")
    var name: String? = null,
) : Parcelable
