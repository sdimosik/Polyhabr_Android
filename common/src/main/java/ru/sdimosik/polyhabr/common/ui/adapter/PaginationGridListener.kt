package ru.sdimosik.polyhabr.common.ui.adapter

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationGridListener(
    private val layoutManager: LinearLayoutManager,
    val pageSize: Int
) :
    RecyclerView.OnScrollListener() {

    private var isScrolling = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

        val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isNotAtBeginning = firstVisibleItemPosition >= 0
        val isTotalMoreThanVisible = totalItemCount >= pageSize

        val shouldPaginate =
            isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

        if (!isLoading()) {
            if (shouldPaginate) {
                loadMoreItems()
                isScrolling = false
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true
        }
    }

    protected abstract fun loadMoreItems()

    protected abstract fun isLoading(): Boolean
}