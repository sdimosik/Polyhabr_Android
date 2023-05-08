package ru.sdimosik.polyhabr.presentaion.main.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.databinding.FragmentFeedBinding

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.fragment_feed) {
    companion object {
        fun newInstance() = FeedFragment()
    }

    val binding by viewBinding(FragmentFeedBinding::bind)

    private val viewModel by viewModels<FeedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setup()
    }

    private fun FragmentFeedBinding.setup(){
        viewModel.articles.observe(viewLifecycleOwner) {

        }
    }

}