package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediadmin.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersScreenViewModel @Inject constructor(private val getAllUsersUseCase: GetAllUsersUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<UsersScreenState>(UsersScreenState.Idle)
    val state = _state.asStateFlow()

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UsersScreenState.Loading
            getAllUsersUseCase().collect { apiOperation ->
                apiOperation.onSuccess { users ->
                    _state.value = UsersScreenState.Success(
                        users = users
                    )
                }.onFailure { exception ->
                    _state.value = UsersScreenState.Failure(
                        error = exception.message ?: "Unknown error!"
                    )
                }
            }
        }
    }
}



































