package ru.sdimosik.polyhabr.data.network.model.comment

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    @SerializedName("text")
    var text: String? = null,
    @SerializedName("articleId")
    var articleId: Long? = null,
)
