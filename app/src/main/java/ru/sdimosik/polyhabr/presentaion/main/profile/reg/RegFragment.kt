package ru.sdimosik.polyhabr.presentaion.main.profile.reg

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
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
import ru.sdimosik.polyhabr.databinding.FragmentRegistrationBinding
import ru.sdimosik.polyhabr.domain.model.toUI

@AndroidEntryPoint
class RegFragment : BaseFragment(R.layout.fragment_registration) {
    companion object {
        fun newInstance() = RegFragment()
    }

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    private val viewModel by viewModels<RegViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setup()
            subscribe()
        }
    }

    private fun FragmentRegistrationBinding.setup() {

    }

    private fun FragmentRegistrationBinding.subscribe() {
        tietLoginUser.doAfterTextChanged {
            viewModel.login = it.toString()
        }
        tietPassUser.doAfterTextChanged {
            viewModel.password = it.toString()
        }
        tietFirstNameUser.doAfterTextChanged {
            viewModel.firstName = it.toString()
        }
        tietLastNameUser.doAfterTextChanged {
            viewModel.lastName = it.toString()
        }
        tietEmailsUser.doAfterTextChanged {
            viewModel.email = it.toString()
        }
        btnGoRegister.setOnClickListener {
            viewModel.register()
        }
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                btnGoRegister.showLoading()
            } else {
                btnGoRegister.hideLoading()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.goConfirmScreen.collectLatest {
                    val bundle = bundleOf("newUser" to it)
                    findNavController().navigate(
                        R.id.action_fragment_reg_to_fragment_confirm_reg,
                        bundle
                    )
                }
            }
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