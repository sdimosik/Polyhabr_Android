package ru.sdimosik.polyhabr.data.network.model.article_type

import com.google.gson.annotations.SerializedName

data class ArticleTypeListResponse(
    @SerializedName("contents")
    val contents: List<ArticleTypeResponse>,
    @SerializedName("totalElements")
    val totalElements: Long,
    @SerializedName("totalPages")
    val totalPages: Int,
)
