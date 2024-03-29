package ru.sdimosik.polyhabr.data.network.model.tag_type

import com.google.gson.annotations.SerializedName

data class TagTypeListResponse(
    @SerializedName("contents")
    val contents: List<TagTypeResponse>,
    @SerializedName("totalElements")
    val totalElements: Long,
    @SerializedName("totalPages")
    val totalPages: Int,
)
