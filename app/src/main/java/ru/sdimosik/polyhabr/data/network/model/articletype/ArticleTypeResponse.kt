package ru.sdimosik.polyhabr.data.network.model.articletype

import com.google.gson.annotations.SerializedName

data class ArticleTypeResponse(
    @SerializedName("name")
    var name: String,
)
