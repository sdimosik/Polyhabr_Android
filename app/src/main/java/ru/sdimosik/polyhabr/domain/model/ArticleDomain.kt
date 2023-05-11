package ru.sdimosik.polyhabr.domain.model

import android.os.Parcelable
import androidx.navigation.NavArgs
import kotlinx.parcelize.Parcelize
import ru.sdimosik.polyhabr.data.network.model.article.ArticleType
import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse
import ru.sdimosik.polyhabr.presentaion.main.feed.adapter.ArticleItem
import ru.sdimosik.polyhabr.presentaion.main.feed.adapter.MicroUI

@Parcelize
data class ArticleDomain(
    val id: Long,
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
    val isLiked: Boolean?
) : Parcelable

fun ArticleDomain.toUI() = ArticleItem(
    id = id,
    date = date,
    user = user,
    title = title,
    previewText = previewText,
    likesCount = likes,
    viewCount = viewCount,
    isSaveToFavourite = isSaveToFavourite,
    type = typeId,
    listDisciplineName = listDisciplineName?.map { MicroUI(it) },
    listTag = listTag.map { MicroUI(it) },
    isLike = isLiked,
    text = text,
)