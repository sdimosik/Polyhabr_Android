package ru.sdimosik.polyhabr.data.network.model.file

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("data")
    var data: ByteArray? = null,
    @SerializedName("createdAt")
    var createdAt: Long? = null
)
