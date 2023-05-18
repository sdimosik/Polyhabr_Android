package ru.sdimosik.polyhabr.data.network.model.article_type

import com.google.gson.annotations.SerializedName

data class ArticleTypeResponse(
    @SerializedName("name")
    var name: String,
)
