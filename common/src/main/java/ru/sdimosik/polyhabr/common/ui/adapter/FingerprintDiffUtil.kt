package ru.sdimosik.polyhabr.common.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class FingerprintDiffUtil(
    private val fingerprints: List<ItemFingerprint<*, *>>,
) : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        if (oldItem::class != newItem::class) return false
        return getListItemCallback(oldItem).areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldListItem: ListItem, newListItem: ListItem): Boolean {
        if (oldListItem::class != newListItem::class) return false

        return getListItemCallback(oldListItem).areContentsTheSame(oldListItem, newListItem)
    }

    override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any? {
        if (oldItem::class != newItem::class) return false

        return getListItemCallback(oldItem).getChangePayload(oldItem, newItem)
    }

    private fun getListItemCallback(
        item: ListItem
    ): DiffUtil.ItemCallback<ListItem> = fingerprints.find { it.isRelativeItem(item) }
        ?.getDiffUtil()
        ?.let { it as DiffUtil.ItemCallback<ListItem> }
        ?: throw IllegalStateException("DiffUtil not found for $item")
}