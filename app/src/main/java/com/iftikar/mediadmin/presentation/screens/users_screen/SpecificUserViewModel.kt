package com.iftikar.mediadmin.presentation.screens.users_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediadmin.domain.usecase.ApproveUserUseCase
import com.iftikar.mediadmin.domain.usecase.GetSpecificUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificUserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSpecificUserUseCase: GetSpecificUserUseCase,
    private val approveUserUseCase: ApproveUserUseCase
) : ViewModel() {
    private val userId: String = checkNotNull(savedStateHandle["userId"])
    private val _state = MutableStateFlow<UsersScreenState>(UsersScreenState.Idle)
    val state = _state.asStateFlow()

    private val _approveUserState = MutableStateFlow<UsersScreenState>(UsersScreenState.Idle)
    val approveUserState = _approveUserState.asStateFlow()

    init {
        getSpecificUser()
    }

    fun getSpecificUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UsersScreenState.Loading
            getSpecificUserUseCase(userId = userId).collect { apiOperation ->
                apiOperation.onSuccess { user ->
                    _state.value = UsersScreenState.Success(
                        data = user
                    )
                }
            }
        }
    }

    fun approveUser(approval: Int ) {
        viewModelScope.launch {
            _approveUserState.value = UsersScreenState.Loading
            approveUserUseCase(userId = userId, approval = approval).onSuccess {
                Log.d("Approve-User", it.message)
                _approveUserState.value = UsersScreenState.Success(
                    data = it
                )
            }.onFailure {
                Log.e("Approve-User", it.message ?: "Unknown Error")
                _approveUserState.value = UsersScreenState.Failure(
                    error = it.message ?: "Unknown Error"
                )
            }
        }
    }
}
