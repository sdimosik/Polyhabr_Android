package ru.sdimosik.polyhabr.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.sdimosik.polyhabr.common.ui.BaseFragment

class ViewPagerWithTabLayoutHelper(
    private val fragment: Fragment,
    private val screens: List<Class<out BaseFragment>>,
    private val screensTittle: List<Int>,
) {

    private val countViewpagerFragment: Int = screens.size

    fun getInstanceAdapter() = object : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = countViewpagerFragment

        override fun createFragment(position: Int): Fragment {
            return getOrderFragment(position)
        }
    }

    private fun getOrderFragment(position: Int): BaseFragment {
        if (!isValidPosition(position)) {
            throw IllegalArgumentException("Illegal position: $position")
        }
        return screens[position].newInstance() as BaseFragment
    }

    private fun isValidPosition(position: Int): Boolean {
        return position >= 0 && position < screens.size
    }

    fun getOrderFragmentTittleId(position: Int): Int {
        if (!isValidPosition(position)) {
            throw IllegalArgumentException("Illegal position: $position")
        }
        return screensTittle[position]
    }
}