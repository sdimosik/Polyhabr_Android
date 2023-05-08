package ru.sdimosik.polyhabr.presentaion.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

): ViewModel() {
    companion object {
        private const val DELAY = 1000L
    }

    private val _command = MutableStateFlow<SplashActions?>(null)
    val command = _command.asStateFlow()

    init {
        viewModelScope.launch {
            delay(DELAY)
            _command.value = SplashActions.TO_MAIN
        }
    }
}