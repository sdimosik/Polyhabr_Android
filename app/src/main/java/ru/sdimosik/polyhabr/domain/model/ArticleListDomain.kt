package ru.sdimosik.polyhabr.domain.model

data class ArticleListDomain(
    val contents: List<ArticleDomain>?,
    val totalElements: Long?,
    val totalPages: Int?,
)