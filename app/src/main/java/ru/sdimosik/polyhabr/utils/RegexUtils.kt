package ru.sdimosik.polyhabr.utils

object RegexUtils {
    val loginRegex = "^[A-Za-z0-9]+\$".toRegex()
    val passwordRegex = "^[A-Za-z0-9]+\$".toRegex()
    val nameRegex = "^[A-Za-z]+\$".toRegex()
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
}