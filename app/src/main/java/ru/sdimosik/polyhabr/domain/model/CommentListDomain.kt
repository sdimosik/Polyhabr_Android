package ru.sdimosik.polyhabr.domain.model

import ru.sdimosik.polyhabr.data.network.model.comment.CommentResponse

data class CommentListDomain(
    val contents: List<CommentDomain>,
    val totalElements: Long,
    val totalPages: Int,
)
