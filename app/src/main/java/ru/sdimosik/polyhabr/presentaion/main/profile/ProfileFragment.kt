package ru.sdimosik.polyhabr.presentaion.main.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.BaseFragment
import ru.sdimosik.polyhabr.databinding.FragmentProfileBinding
import ru.sdimosik.polyhabr.presentaion.main.feed.adapter.ArticleItem

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel by viewModels<ProfileViewModel>()


    private val exit by lazy {
        arguments?.getBoolean("exit", false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setup()
            subscribe()
        }
    }

    private fun FragmentProfileBinding.setup() {

    }

    private fun FragmentProfileBinding.subscribe() {
        tietNameUser.addTextChangedListener {
            viewModel.login = it.toString()
        }
        tietPassUser.addTextChangedListener {
            viewModel.password = it.toString()
        }
        btnLogin.setOnClickListener {
            viewModel.goLogin()
        }
        btnGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_to_fragment_reg)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest {
                    switchToastStyleToError(it)
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                btnLogin.showLoading()
            } else {
                btnLogin.hideLoading()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.goMyProfile.collectLatest {
                    if (it) {
                        findNavController().navigate(R.id.action_fragment_profile_to_fragment_my_profile)
                    } else {
                        showAuthScreen()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (exit == true) {
            binding.showAuthScreen()
        } else {
            viewModel.checkAuth()
        }
    }

    private fun FragmentProfileBinding.showAuthScreen() {
        clLoadingProfile.visibility = View.GONE
        clAuth.visibility = View.VISIBLE
    }
}