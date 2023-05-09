package ru.sdimosik.polyhabr.presentaion.main.article_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.adapter.BaseViewHolder
import ru.sdimosik.polyhabr.common.ui.adapter.ItemFingerprint
import ru.sdimosik.polyhabr.common.ui.adapter.ListItem
import ru.sdimosik.polyhabr.databinding.ItemCommentBinding

class CommentDelegate : ItemFingerprint<ItemCommentBinding, CommentItem> {
    override fun isRelativeItem(item: ListItem): Boolean {
        return item is CommentItem
    }

    override fun getLayoutId(): Int {
        return R.layout.item_comment
    }

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemCommentBinding, CommentItem> {
        val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
        return CommentViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<CommentItem> {
        return diffUtil
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CommentItem>() {

        override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem) =
            oldItem == newItem
    }
}

class CommentViewHolder(
    binding: ItemCommentBinding
) : BaseViewHolder<ItemCommentBinding, CommentItem>(binding) {
    override fun onBind(item: CommentItem) {
        super.onBind(item)
        with(binding) {
            tvTextComment.text = item.text
            tvCommentAuthor.text = item.login
            tvDateComment.text = item.date
        }
    }
}