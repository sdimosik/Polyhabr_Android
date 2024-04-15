package ru.sdimosik.polyhabr.presentaion.main.articledetail.adapter

import ru.sdimosik.polyhabr.common.ui.adapter.ListItem

data class CommentItem(
    val id: Long,
    val login: String,
    val articleId: Long,
    val userId: Long,
    val date: String,
    val text: String,
) : ListItem
