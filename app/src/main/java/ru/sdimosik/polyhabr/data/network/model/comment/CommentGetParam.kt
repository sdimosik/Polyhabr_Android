package ru.sdimosik.polyhabr.data.network.model.comment

data class CommentGetParam(
    val articleId: Long,
    val offset: Int,
    val size: Int,
)
