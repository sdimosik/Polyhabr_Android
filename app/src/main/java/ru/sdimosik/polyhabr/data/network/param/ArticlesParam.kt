package ru.sdimosik.polyhabr.data.network.param

data class ArticlesParam(
    val offset: Int,
    val size: Int,
    val sorting: SortArticleRequest? = null,
)
