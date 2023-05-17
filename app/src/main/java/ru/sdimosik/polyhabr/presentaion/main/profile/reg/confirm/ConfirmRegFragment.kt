package ru.sdimosik.polyhabr.presentaion.main.profile.reg.confirm

import android.os.Bundle
import android.view.View
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
import ru.sdimosik.polyhabr.data.network.model.user.NewUser
import ru.sdimosik.polyhabr.databinding.FragmentConfirmRegBinding

@AndroidEntryPoint
class ConfirmRegFragment : BaseFragment(R.layout.fragment_confirm_reg) {

    companion object {
        fun newInstance() = ConfirmRegFragment()
    }

    private val binding by viewBinding(FragmentConfirmRegBinding::bind)

    private val viewModel by viewModels<ConfirmRegViewModel>()

    private val newUser by lazy {
        arguments?.getParcelable<NewUser>("newUser")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setup()
            subscribe()
        }
    }

    private fun FragmentConfirmRegBinding.setup() {

    }

    private fun FragmentConfirmRegBinding.subscribe() {
        tietVerifyCode.doAfterTextChanged {
            viewModel.verifyCode = it.toString()
        }
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        lbVerifyReg.setOnClickListener {
            viewModel.verify(newUser)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                lbVerifyReg.showLoading()
            } else {
                lbVerifyReg.hideLoading()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.goMyProfile.collectLatest {
                    if (it) {
                        findNavController().navigate(R.id.action_fragment_confirm_reg_to_fragment_my_profile)
                    }
                }
            }
        }
    }
}