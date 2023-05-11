package ru.sdimosik.polyhabr.data.network.model.comment

import com.google.gson.annotations.SerializedName
import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse
import ru.sdimosik.polyhabr.domain.model.CommentDomain

data class CommentResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("text")
    val text: String,
    @SerializedName("articleId")
    val articleId: Long,
    @SerializedName("userId")
    val userId: UserOtherResponse,
    @SerializedName("data")
    val data: String,
)

fun CommentResponse.toDomain() = CommentDomain(
    id = id,
    text = text,
    articleId = articleId,
    userId = userId,
    data = data,
)