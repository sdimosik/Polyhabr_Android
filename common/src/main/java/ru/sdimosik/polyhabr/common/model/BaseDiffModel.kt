package ru.sdimosik.polyhabr.common.model

interface BaseDiffModel {
    val id: Long
    fun isIdEqual(other: BaseDiffModel): Boolean = id == other.id
}
