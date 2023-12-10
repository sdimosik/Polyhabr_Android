package ru.sdimosik.polyhabr.data.network.model.comment

import com.google.gson.annotations.SerializedName

data class CommentCreateResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("id")
    val id: Long? = null,
)
