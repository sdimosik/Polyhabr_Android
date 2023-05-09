package ru.sdimosik.polyhabr.presentaion.main.feed.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.sdimosik.polyhabr.common.ui.adapter.ListItem
import ru.sdimosik.polyhabr.data.network.model.article.ArticleType
import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse

@Parcelize
data class ArticleItem(
    val id: Long,
    val date: String?,
    val user: UserOtherResponse? = null,
    val title: String? = null,
    val previewText: String?,
    val text: String? = null,

    val isLike: Boolean?,
    val likesCount: Int?,
    val viewCount: Long?,
    val isSaveToFavourite: Boolean?,

    val type: ArticleType?,
    val listDisciplineName: List<MicroUI>?,
    val listTag: List<MicroUI>,
) : ListItem, Parcelable