package ru.sdimosik.polyhabr.presentaion.main.article_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.extensions.hide
import ru.sdimosik.polyhabr.common.extensions.show
import ru.sdimosik.polyhabr.common.ui.BaseFragment
import ru.sdimosik.polyhabr.common.ui.adapter.PaginationGridListener
import ru.sdimosik.polyhabr.databinding.FragmentArticleDetailBinding
import ru.sdimosik.polyhabr.presentaion.main.article_detail.adapter.CommentAdapter
import ru.sdimosik.polyhabr.presentaion.main.feed.adapter.ArticleItem
import ru.sdimosik.polyhabr.presentaion.main.feed.adapter.MicroAdapter

@AndroidEntryPoint
class ArticleDetailFragment : BaseFragment(R.layout.fragment_article_detail) {

    private val binding by viewBinding(FragmentArticleDetailBinding::bind)

    private val viewModel by viewModels<ArticleDetailViewModel>()

    private val article by lazy {
        arguments?.getParcelable<ArticleItem>("article")!!
    }


    private val commentAdapter by lazy {
        CommentAdapter()
    }

    private val disciplineAdapter by lazy { MicroAdapter() }
    private val tagAdapter by lazy { MicroAdapter() }
    private val sharedViewPool by lazy { RecyclerView.RecycledViewPool() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setup()
        binding.subscribe()
    }

    private fun FragmentArticleDetailBinding.setup() {
        viewModel.loadComments(article.id)
        tvHeaderTitle.text = article.type?.name ?: "Публикация"
        rvComments.apply {
            adapter = commentAdapter
            val lm = this.layoutManager as LinearLayoutManager
            val paginatation =
                object : PaginationGridListener(lm, viewModel.pageSize) {
                    override fun loadMoreItems() {
                        viewModel.loadComments(article.id)
                    }

                    override fun isLoading(): Boolean = viewModel.isLoading.value == true
                }
            addOnScrollListener(paginatation)
        }
        rvDiscipline.apply {
            adapter = disciplineAdapter
            setRecycledViewPool(sharedViewPool)
        }
        rvTag.apply {
            adapter = tagAdapter
            setRecycledViewPool(sharedViewPool)
        }
        if (article.listDisciplineName.isNullOrEmpty()) {
            rvDiscipline.hide()
        } else {
            rvDiscipline.show()
            disciplineAdapter.submitList(article.listDisciplineName)
        }

        if (article.listTag.isEmpty()) {
            rvTag.hide()
        } else {
            rvTag.show()
            tagAdapter.submitList(article.listTag)
        }

        tvTitle.text = article.title
        tvPreviewText.text = article.text
        tvDate.text = article.date
        tvAuthor.text = article.user?.login
        tvViewCount.text = article.viewCount.toString()
        tvLikeCount.text = article.likesCount.toString()

        acivLike.setImageResource(
            if (article.isLike == true) {
                R.drawable.ic_like_pressed
            } else {
                R.drawable.ic_like_not_pressed
            }
        )
        acivFav.setImageResource(
            if (article.isSaveToFavourite == true) {
                R.drawable.ic_fav_pressed
            } else {
                R.drawable.ic_fav_not_pressed
            }
        )
    }

    private fun FragmentArticleDetailBinding.subscribe() {
        btnCreateComment.setOnClickListener {
            val text = tietCommentCreate.text.toString()
            viewModel.createComment(article.id, text)
        }
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        viewModel.comments.observe(viewLifecycleOwner) { list ->
            commentAdapter.submitList(list)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest {
                    switchToastStyleToError(it)
                }
            }
        }
        acivLike.setOnClickListener {
            val goLike = (viewModel.isLiked.value ?: article.isLike) != true
            viewModel.changeLike(article.id, goLike)
        }
        acivFav.setOnClickListener {
            val goFav = (viewModel.isFavorite.value ?: article.isSaveToFavourite) != true
            viewModel.changeFavourite(article.id, goFav)
        }
        viewModel.isLiked.observe(viewLifecycleOwner) {
            acivLike.setImageResource(
                if (it == true) {
                    R.drawable.ic_like_pressed
                } else {
                    R.drawable.ic_like_not_pressed
                }
            )
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            acivFav.setImageResource(
                if (it == true) {
                    R.drawable.ic_fav_pressed
                } else {
                    R.drawable.ic_fav_not_pressed
                }
            )
        }
    }
}