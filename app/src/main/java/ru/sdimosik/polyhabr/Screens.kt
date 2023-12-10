package ru.sdimosik.polyhabr

import android.content.Context
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.sdimosik.polyhabr.presentaion.main.create.CreateFragment
import ru.sdimosik.polyhabr.presentaion.main.feed.FeedFragment
import ru.sdimosik.polyhabr.presentaion.main.profile.ProfileFragment

class Screens {

    class FeedScreen : FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = FeedFragment.newInstance()
    }

    class CreateScreen : FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CreateFragment.newInstance()
    }

    class ProfileScreen : FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = ProfileFragment.newInstance()
    }
}
