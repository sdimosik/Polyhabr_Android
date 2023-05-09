package ru.sdimosik.polyhabr.data.network.model.comment

data class CommentRequest(
    var text: String? = null,
    var articleId: Long? = null,
)