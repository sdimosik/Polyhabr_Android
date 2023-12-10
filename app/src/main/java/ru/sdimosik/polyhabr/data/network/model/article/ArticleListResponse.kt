package ru.sdimosik.polyhabr.data.network.model.article

import com.google.gson.annotations.SerializedName

data class ArticleListResponse(
    @SerializedName("contents")
    val contents: List<ArticleResponse>?,
    @SerializedName("totalElements")
    val totalElements: Long?,
    @SerializedName("totalPages")
    val totalPages: Int?,
)
