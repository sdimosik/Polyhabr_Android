package ru.sdimosik.polyhabr.presentaion.main.feed.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.sdimosik.polyhabr.common.ui.adapter.ListItem

@Parcelize
data class MicroUI(
    val text: String
) : ListItem, Parcelable
