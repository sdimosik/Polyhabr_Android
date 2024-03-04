package ru.sdimosik.polyhabr.presentaion.main.articledetail.adapter

import ru.sdimosik.polyhabr.common.ui.adapter.FingerprintAdapter

class CommentAdapter : FingerprintAdapter(
    listOf(
        CommentDelegate()
    )
)
