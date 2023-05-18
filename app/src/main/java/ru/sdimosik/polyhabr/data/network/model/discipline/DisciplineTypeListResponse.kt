package ru.sdimosik.polyhabr.data.network.model.discipline

import com.google.gson.annotations.SerializedName

data class DisciplineTypeListResponse(
    @SerializedName("contents")
    val contents: List<DisciplineTypeResponse>,
    @SerializedName("totalElements")
    val totalElements: Long,
    @SerializedName("totalPages")
    val totalPages: Int,
)
