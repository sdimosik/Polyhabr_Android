package ru.sdimosik.polyhabr

enum class ScreenType(val type: Int) {
    FEED_BASE(0),
    CREATE_BASE(1),
    PROFILE_BASE(2);

    companion object {
        fun getType(value: Int) = values().firstOrNull { it.type == value } ?: FEED_BASE
    }
}