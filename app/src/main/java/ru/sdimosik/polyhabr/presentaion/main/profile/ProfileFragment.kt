package ru.sdimosik.polyhabr.presentaion.main.profile

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.sdimosik.polyhabr.R

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    companion object {
        fun newInstance() = ProfileFragment()
    }
}