package ru.sdimosik.polyhabr.presentaion.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.extensions.setupWithNavController
import ru.sdimosik.polyhabr.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private var currentNavController: LiveData<NavController>? = null

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.feed__nav_graph,
            R.navigation.create__nav_graph,
            R.navigation.profile__nav_graph
        )

        val controller = binding.fragmentMainBottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = requireActivity().supportFragmentManager,
            containerId = R.id.fragment_main__nav_host_container,
            intent = requireActivity().intent
        )

        currentNavController = controller
        currentNavController?.observe(viewLifecycleOwner) { liveDataController ->
            Navigation.setViewNavController(requireView(), liveDataController)
        }
    }

}