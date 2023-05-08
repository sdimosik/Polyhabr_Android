package ru.sdimosik.polyhabr.presentaion.main.create

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.sdimosik.polyhabr.R

@AndroidEntryPoint
class CreateFragment : Fragment(R.layout.fragment_create) {
    companion object {
        fun newInstance() = CreateFragment()
    }
}