package ru.sdimosik.polyhabr.common.ui.adapter

interface PayloadSealed

fun PayloadSealed.getKey(): String = this::class.java.name