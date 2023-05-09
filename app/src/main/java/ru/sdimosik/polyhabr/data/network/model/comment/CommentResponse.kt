package ru.sdimosik.polyhabr.data.network.model.comment

import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse
import ru.sdimosik.polyhabr.domain.model.CommentDomain

data class CommentResponse(
    val id: Long,
    val text: String,
    val articleId: Long,
    val userId: UserOtherResponse,
    val data: String,
)

fun CommentResponse.toDomain() = CommentDomain(
    id = id,
    text = text,
    articleId = articleId,
    userId = userId,
    data = data,
)