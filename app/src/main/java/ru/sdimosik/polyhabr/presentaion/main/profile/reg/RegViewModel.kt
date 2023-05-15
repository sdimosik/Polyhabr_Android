package ru.sdimosik.polyhabr.presentaion.main.profile.reg

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import javax.inject.Inject

class RegViewModel @Inject constructor(
    private val authInteractor: IAuthInteractor
) : BaseViewModel() {
    private val _error = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val error = _error.asSharedFlow()
}