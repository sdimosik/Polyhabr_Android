package ru.sdimosik.polyhabr.presentaion.main.profile.setting

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
import ru.sdimosik.polyhabr.databinding.FragmentSettingProfileBinding

@AndroidEntryPoint
class SettingFragment : BaseFragment(R.layout.fragment_setting_profile) {

    private val binding by viewBinding(FragmentSettingProfileBinding::bind)

    private val viewModel by viewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            subscribe()
        }
    }

    private fun FragmentSettingProfileBinding.subscribe() {
        tietOldPassUser.doAfterTextChanged {
            viewModel.oldPass = it.toString()
        }
        tietNewPassUser.doAfterTextChanged {
            viewModel.newPass = it.toString()
        }
        tietRetryPassUser.doAfterTextChanged {
            viewModel.retryPass = it.toString()
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
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btnExit.setOnClickListener {
            viewModel.exitFromAcc()
        }
        btnSave.setOnClickListener {
            viewModel.updateInfo()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                btnSave.showLoading()
            } else {
                btnSave.hideLoading()
            }
        }
        viewModel.meUser.observe(viewLifecycleOwner) {
            tietFirstNameUser.setText(it.name)
            tietLastNameUser.setText(it.surname)
            tietEmailsUser.setText(it.email)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.goBaseAuth.collectLatest {
                    val param = bundleOf("exit" to true)
                    findNavController().navigate(
                        R.id.action_fragment_profile_setting_to_fragment_profile,
                        param
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.goBack.collectLatest {
                    requireActivity().onBackPressed()
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
