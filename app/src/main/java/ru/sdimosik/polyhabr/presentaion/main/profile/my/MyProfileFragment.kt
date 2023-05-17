package ru.sdimosik.polyhabr.presentaion.main.profile.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.BaseFragment
import ru.sdimosik.polyhabr.databinding.FragmentConfirmRegBinding
import ru.sdimosik.polyhabr.databinding.FragmentMyProfileBinding
import ru.sdimosik.polyhabr.databinding.FragmentRegistrationBinding
import ru.sdimosik.polyhabr.presentaion.main.profile.my.tabs.FavArticlesFragment
import ru.sdimosik.polyhabr.presentaion.main.profile.my.tabs.MyArticlesFragment
import ru.sdimosik.polyhabr.presentaion.main.profile.reg.RegViewModel
import ru.sdimosik.polyhabr.presentaion.main.profile.reg.confirm.ConfirmRegFragment
import ru.sdimosik.polyhabr.presentaion.main.profile.reg.confirm.ConfirmRegViewModel
import ru.sdimosik.polyhabr.utils.ViewPagerWithTabLayoutHelper

@AndroidEntryPoint
class MyProfileFragment : BaseFragment(R.layout.fragment_my_profile) {
    companion object {
        fun newInstance() = MyProfileFragment()

        private val screens = listOf(
            MyArticlesFragment::class.java,
            FavArticlesFragment::class.java
        )

        private val screensTittle = listOf(
            R.string.fragment_profile_tab_my_articles,
            R.string.fragment_profile_tab_fav_articles
        )
    }

    private val binding by viewBinding(FragmentMyProfileBinding::bind)

    private val viewModel by viewModels<MyProfileViewModel>()

    private val helper: ViewPagerWithTabLayoutHelper by lazy {
        ViewPagerWithTabLayoutHelper(
            this,
            screens,
            screensTittle
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setup()
            subscribe()
        }
    }

    private fun FragmentMyProfileBinding.setup() {
        vp2ArticlesFragment.adapter = helper.getInstanceAdapter()
        TabLayoutMediator(tlMyArticles, vp2ArticlesFragment) { tab, position ->
            tab.text = getString(helper.getOrderFragmentTittleId(position))
        }.attach()
    }

    private fun FragmentMyProfileBinding.subscribe() {
        ivSetting.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_my_profile_to_fragment_profile_setting)
        }
    }
}