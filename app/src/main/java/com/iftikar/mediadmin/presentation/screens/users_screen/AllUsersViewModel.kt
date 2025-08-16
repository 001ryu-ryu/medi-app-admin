package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediadmin.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllUsersViewModel @Inject constructor(private val getAllUsersUseCase: GetAllUsersUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(AllUsersState())
    val state = _state.asStateFlow()

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            getAllUsersUseCase().collect { apiOperation ->
                apiOperation.onSuccess { users ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            users = users
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
}



































