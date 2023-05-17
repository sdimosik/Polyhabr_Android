package ru.sdimosik.polyhabr.data.network.model.comment

import com.google.gson.annotations.SerializedName

data class CommentGetParam(
    @SerializedName("articleId")
    val articleId: Long,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("size")
    val size: Int,
)
