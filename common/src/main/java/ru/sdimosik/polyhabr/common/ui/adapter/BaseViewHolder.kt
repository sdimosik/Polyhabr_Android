package ru.sdimosik.polyhabr.common.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<out V : ViewBinding, I : ListItem>(
    open val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    protected open lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }

    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }

    open fun onViewRecycled() {}
}