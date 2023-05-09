package ru.sdimosik.polyhabr.presentaion.main.feed

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hjq.toast.ToastParams
import com.hjq.toast.Toaster
import com.hjq.toast.style.CustomToastStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.BaseFragment
import ru.sdimosik.polyhabr.common.ui.adapter.PaginationGridListener
import ru.sdimosik.polyhabr.databinding.FragmentFeedBinding
import ru.sdimosik.polyhabr.domain.model.toUI
import ru.sdimosik.polyhabr.presentaion.main.feed.adapter.FeedAdapter
import java.util.stream.Stream
import javax.inject.Inject


@AndroidEntryPoint
class FeedFragment : BaseFragment(R.layout.fragment_feed) {
    companion object {
        fun newInstance() = FeedFragment()
    }

    private val binding by viewBinding(FragmentFeedBinding::bind)

    private val viewModel by viewModels<FeedViewModel>()

    private val feedAdapter by lazy {
        FeedAdapter(
            onClick = {
                viewModel.getDetailArticle(it.id)
            },
            onLike = { id, goLike -> viewModel.changeLike(id, goLike) },
            onFav = { id, goFav -> viewModel.changeFavourite(id, goFav) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setup()
        binding.subscribe()
    }

    private fun FragmentFeedBinding.setup() {
        rvArticles.apply {
            adapter = feedAdapter
            val lm = this.layoutManager as LinearLayoutManager
            val paginatation =
                object : PaginationGridListener(lm, viewModel.pageSize) {
                    override fun loadMoreItems() {
                        viewModel.loadArticles()
                    }

                    override fun isLoading(): Boolean = viewModel.isLoading.value == true
                }
            addOnScrollListener(paginatation)
        }
    }

    private fun FragmentFeedBinding.subscribe() {
        viewModel.articles.observe(viewLifecycleOwner) { list ->
            feedAdapter.submitList(list)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest {
                    switchToastStyleToError(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.goToDetail.collectLatest {
                    val bundle = bundleOf("article" to it.toUI())
                    findNavController().navigate(
                        R.id.action_fragment_feed_to_fragment_detail_article,
                        bundle
                    )
                }
            }
        }
    }
}
