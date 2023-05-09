package ru.sdimosik.polyhabr.common.ui.adapter

interface BaseDiffModel {
    val id: Long
    fun isIdEqual(other: BaseDiffModel): Boolean = id == other.id
}