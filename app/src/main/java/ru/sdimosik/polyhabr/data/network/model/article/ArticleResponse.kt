package ru.sdimosik.polyhabr.data.network.model.article

import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse

data class ArticleResponse(
    val id: Long?,
    val date: String?,
    val filePdf: String? = null,
    val likes: Int?,
    val previewText: String?,
    val typeId: ArticleType?,
    val user: UserOtherResponse? = null,
    val title: String? = null,
    val text: String? = null,
    val listDisciplineName: List<String>?,
    val listTag: List<String>,
    val fileId: String? = null,
    val viewCount: Long?,
    val isSaveToFavourite: Boolean?,
    val pdfId: String?,
    val previewImgId: String?,
)