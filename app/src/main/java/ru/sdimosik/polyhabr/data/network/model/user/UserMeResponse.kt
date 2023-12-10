package ru.sdimosik.polyhabr.data.network.model.user

data class UserMeResponse(
    val id: Long,
    val email: String,
    val login: String,
    val name: String,
    val surname: String,
)
