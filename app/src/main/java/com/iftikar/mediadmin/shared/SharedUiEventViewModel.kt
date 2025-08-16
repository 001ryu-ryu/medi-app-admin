package com.iftikar.mediadmin.shared

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SharedUiEventViewModel @Inject constructor() : ViewModel() {
    private val _userApprovedFlow = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val userApprovedFlow = _userApprovedFlow.asSharedFlow()

    fun emitUserApproved() {
        _userApprovedFlow.tryEmit(true)
    }
}