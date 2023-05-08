package ru.sdimosik.polyhabr.data.network.model.article

data class ArticleListResponse(
    val contents: List<ArticleResponse>?,
    val totalElements: Long?,
    val totalPages: Int?,
)