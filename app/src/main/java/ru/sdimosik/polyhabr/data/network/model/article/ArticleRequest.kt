package ru.sdimosik.polyhabr.data.network.model.article

data class ArticleRequest(
    val title: String,
    val text: String,
    val previewText: String,
    val articleType: String,
    val listDisciplineName: List<String>,
    val listTag: List<String>,
)