package ru.sdimosik.polyhabr.presentaion.main.feed.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.extensions.hide
import ru.sdimosik.polyhabr.common.extensions.show
import ru.sdimosik.polyhabr.common.ui.adapter.*
import ru.sdimosik.polyhabr.databinding.ItemArticleBinding

class ArticleDelegate(
    private val onClick: (ArticleItem) -> Unit,
    private val onLike: (Long, Boolean) -> Unit,
    private val onFav: (Long, Boolean) -> Unit,
    private val sharedViewPool: RecyclerView.RecycledViewPool,
) : ItemFingerprint<ItemArticleBinding, ArticleItem> {

    sealed class PayloadType : PayloadSealed {
        object BooleanIsInLikes : PayloadType()
        object IntCountLikes : PayloadType()
        object IntCountView : PayloadType()
        object BooleanIsInFav : PayloadType()
    }

    override fun isRelativeItem(item: ListItem) = item is ArticleItem

    override fun getLayoutId() = R.layout.item_article

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemArticleBinding, ArticleItem> {
        val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding, onClick, onLike, onFav, sharedViewPool)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ArticleItem>() {

        override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem) =
            oldItem == newItem

        override fun getChangePayload(oldItem: ArticleItem, newItem: ArticleItem): Any? {
            val bundle = Bundle()
            if (oldItem.isLike != newItem.isLike) return bundle.apply {
                putBoolean(PayloadType.BooleanIsInLikes.getKey(), newItem.isLike ?: false)
            }
            if (oldItem.likesCount != newItem.likesCount) return bundle.apply {
                putInt(PayloadType.IntCountLikes.getKey(), newItem.likesCount ?: 0)
            }
            if (oldItem.viewCount != newItem.viewCount) return bundle.apply {
                putLong(PayloadType.IntCountView.getKey(), newItem.viewCount ?: 0)
            }
            if (oldItem.isSaveToFavourite != newItem.isSaveToFavourite) return bundle.apply {
                putBoolean(PayloadType.BooleanIsInFav.getKey(), newItem.isSaveToFavourite ?: false)
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }
}

class ArticleViewHolder(
    binding: ItemArticleBinding,
    onClick: (ArticleItem) -> Unit,
    onLike: (Long, Boolean) -> Unit,
    onFav: (Long, Boolean) -> Unit,
    private val sharedViewPool: RecyclerView.RecycledViewPool
) : BaseViewHolder<ItemArticleBinding, ArticleItem>(binding) {

    private val disciplineAdapter = MicroAdapter()
    private val tagAdapter = MicroAdapter()

    init {
        with(binding) {
            tvMoreDetail.setOnClickListener {
                onClick(item)
            }
            acivLike.setOnClickListener {
                onLike(item.id, item.isLike != true)
            }
            acivFav.setOnClickListener {
                onFav(item.id, item.isSaveToFavourite != true)
            }
            rvDiscipline.apply {
                adapter = disciplineAdapter
                setRecycledViewPool(sharedViewPool)
            }
            rvTag.apply {
                adapter = tagAdapter
                setRecycledViewPool(sharedViewPool)
            }
        }
    }

    override fun onBind(item: ArticleItem) {
        super.onBind(item)
        binding.apply {

            if (item.listDisciplineName.isNullOrEmpty()) {
                rvDiscipline.hide()
            } else {
                rvDiscipline.show()
                disciplineAdapter.submitList(item.listDisciplineName)
            }

            if (item.listTag.isEmpty()) {
                rvTag.hide()
            } else {
                rvTag.show()
                tagAdapter.submitList(item.listTag)
            }

            tvTitle.text = item.title
            tvPreviewText.text = item.previewText
            tvDate.text = item.date
            tvAuthor.text = item.user?.login
            tvViewCount.text = item.viewCount.toString()
            tvLikeCount.text = item.likesCount.toString()
            tvType.text = item.type?.name

            acivLike.setImageResource(
                if (item.isLike == true) {
                    R.drawable.ic_like_pressed
                } else {
                    R.drawable.ic_like_not_pressed
                }
            )
            acivFav.setImageResource(
                if (item.isSaveToFavourite == true) {
                    R.drawable.ic_fav_pressed
                } else {
                    R.drawable.ic_fav_not_pressed
                }
            )
        }
    }

    override fun onBind(item: ArticleItem, payloads: List<Any>) {
        super.onBind(item, payloads)
    }
}