package ru.sdimosik.polyhabr.presentaion.main.profile.reg

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.BaseFragment
import ru.sdimosik.polyhabr.databinding.FragmentRegistrationBinding

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

    }
}