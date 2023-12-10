package ru.sdimosik.polyhabr.presentaion.main.feed.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.sdimosik.polyhabr.common.ui.adapter.FingerprintAdapter

class FeedAdapter(
    onClick: (ArticleItem) -> Unit,
    onLike: (Long, Boolean) -> Unit,
    onFav: (Long, Boolean) -> Unit,
    private val sharedViewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool(),
) : FingerprintAdapter(
    listOf(
        ArticleDelegate(onClick, onLike, onFav, sharedViewPool)
    )
)
