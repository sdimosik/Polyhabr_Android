package ru.sdimosik.polyhabr.data.network.model.comment

import com.google.gson.annotations.SerializedName

data class CommentListResponse(
    @SerializedName("contents")
    val contents: List<CommentResponse>,
    @SerializedName("totalElements")
    val totalElements: Long,
    @SerializedName("totalPages")
    val totalPages: Int,
)

fun CommentListResponse.toDomain() = ru.sdimosik.polyhabr.domain.model.CommentListDomain(
    contents = contents.map { it.toDomain() },
    totalElements = totalElements,
    totalPages = totalPages,
)
