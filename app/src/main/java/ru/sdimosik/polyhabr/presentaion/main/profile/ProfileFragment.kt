package ru.sdimosik.polyhabr.presentaion.main.profile

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.BaseFragment
import ru.sdimosik.polyhabr.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel by viewModels<ProfileViewModel>()

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
        btnRegister.setOnClickListener {

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest {
                    switchToastStyleToError(it)
                }
            }
        }
    }
}