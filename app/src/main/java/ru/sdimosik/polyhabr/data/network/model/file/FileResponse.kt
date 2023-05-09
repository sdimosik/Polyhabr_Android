package ru.sdimosik.polyhabr.data.network.model.file

data class FileResponse(
    var id: String? = null,

    var username: String? = null,

    var description: String? = null,

    var name: String? = null,

    var type: String? = null,

    var data: ByteArray? = null,

    var createdAt: Long? = null
)
