package ru.sdimosik.polyhabr.data.network.model.tagtype

import com.google.gson.annotations.SerializedName

data class TagTypeRequest(
    @SerializedName("name")
    var name: String
)
