package com.iftikar.mediadmin.presentation.screens.users_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediadmin.domain.usecase.ApproveUserUseCase
import com.iftikar.mediadmin.domain.usecase.GetSpecificUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificUserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSpecificUserUseCase: GetSpecificUserUseCase,
    private val approveUserUseCase: ApproveUserUseCase
) : ViewModel() {
    private val userId: String = checkNotNull(savedStateHandle["userId"])
    private val _state = MutableStateFlow(SpecificUserState())
    val state = _state.asStateFlow()

    private val _approveUserEvent = MutableSharedFlow<ApproveUserEvent>()
    val approveUserState = _approveUserEvent.asSharedFlow()


    init {
        getSpecificUser()
    }

    fun getSpecificUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            getSpecificUserUseCase(userId = userId).collect { apiOperation ->
                apiOperation.onSuccess { user ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            user = user
                        )
                    }
                }.onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Some error occurred!"
                        )
                    }
                }
            }
        }
    }

    fun approveUser(approval: Int) {
        viewModelScope.launch {

            approveUserUseCase(userId = userId, approval = approval).onSuccess {
                Log.d("Approve-User", it.message)
                viewModelScope.launch(Dispatchers.IO) {
                    _approveUserEvent.emit(
                        ApproveUserEvent.ShowMessage(it.message)
                    )
                }

            }.onFailure {
                Log.e("Approve-User", it.message ?: "Unknown Error")
                viewModelScope.launch(Dispatchers.IO) {
                    _approveUserEvent.emit(
                        ApproveUserEvent.ShowMessage(it.message ?: "Could not approve user")
                    )
                }
            }
        }
    }
}
