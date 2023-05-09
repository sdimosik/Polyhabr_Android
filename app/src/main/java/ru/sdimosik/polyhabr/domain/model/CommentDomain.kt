package ru.sdimosik.polyhabr.domain.model

import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse
import ru.sdimosik.polyhabr.presentaion.main.article_detail.adapter.CommentItem

data class CommentDomain(
    val id: Long,
    val text: String,
    val articleId: Long,
    val userId: UserOtherResponse,
    val data: String,
)

fun CommentDomain.toUI() = CommentItem(
    id = id,
    text = text,
    articleId = articleId,
    userId = userId.id,
    date = data,
    login = userId.login,
)
