package ru.sdimosik.polyhabr.data.network.model.comment

data class CommentListResponse(
    val contents: List<CommentResponse>,
    val totalElements: Long,
    val totalPages: Int,
)

fun CommentListResponse.toDomain() = ru.sdimosik.polyhabr.domain.model.CommentListDomain(
    contents = contents.map { it.toDomain() },
    totalElements = totalElements,
    totalPages = totalPages,
)
