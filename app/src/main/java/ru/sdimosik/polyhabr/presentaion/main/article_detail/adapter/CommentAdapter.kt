package ru.sdimosik.polyhabr.presentaion.main.article_detail.adapter

import ru.sdimosik.polyhabr.common.ui.adapter.FingerprintAdapter

class CommentAdapter : FingerprintAdapter(
    listOf(
        CommentDelegate()
    )
)
