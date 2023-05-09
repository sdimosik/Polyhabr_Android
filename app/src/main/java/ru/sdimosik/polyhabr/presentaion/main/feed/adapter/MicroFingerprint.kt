package ru.sdimosik.polyhabr.presentaion.main.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.adapter.BaseViewHolder
import ru.sdimosik.polyhabr.common.ui.adapter.ItemFingerprint
import ru.sdimosik.polyhabr.common.ui.adapter.ListItem
import ru.sdimosik.polyhabr.databinding.ItemMicroBinding

class MicroFingerprint : ItemFingerprint<ItemMicroBinding, MicroUI> {
    override fun isRelativeItem(item: ListItem): Boolean {
        return item is MicroUI
    }

    override fun getLayoutId(): Int = R.layout.item_micro

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemMicroBinding, MicroUI> {
        val binding = ItemMicroBinding.inflate(layoutInflater, parent, false)
        return MicroViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<MicroUI> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<MicroUI>() {

        override fun areItemsTheSame(oldItem: MicroUI, newItem: MicroUI) =
            oldItem.text == newItem.text

        override fun areContentsTheSame(oldItem: MicroUI, newItem: MicroUI) =
            oldItem == newItem
    }
}

class MicroViewHolder(
    binding: ItemMicroBinding
) : BaseViewHolder<ItemMicroBinding, MicroUI>(binding) {
    override fun onBind(item: MicroUI) {
        super.onBind(item)
        with(binding) {
            root.text = item.text.toString()
        }
    }
}