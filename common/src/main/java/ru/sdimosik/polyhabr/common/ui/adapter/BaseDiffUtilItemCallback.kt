package ru.sdimosik.polyhabr.common.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.sdimosik.polyhabr.common.ui.adapter.BaseDiffModel

class BaseDiffUtilItemCallback : DiffUtil.ItemCallback<BaseDiffModel>() {
    override fun areItemsTheSame(oldItem: BaseDiffModel, newItem: BaseDiffModel): Boolean {
        return oldItem.isIdEqual(newItem)
    }

    override fun areContentsTheSame(oldItem: BaseDiffModel, newItem: BaseDiffModel): Boolean {
        return oldItem.equals(newItem)
    }
}