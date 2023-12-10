package ru.sdimosik.polyhabr.presentaion.main.article_detail.adapter

import ru.sdimosik.polyhabr.common.ui.adapter.ListItem

data class CommentItem(
    val id: Long,
    val login: String,
    val articleId: Long,
    val userId: Long,
    val date: String,
    val text: String,
) : ListItem
