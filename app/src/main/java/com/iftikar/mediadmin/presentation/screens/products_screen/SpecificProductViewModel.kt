package com.iftikar.mediadmin.presentation.screens.products_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediadmin.domain.usecase.GetSpecificProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificProductViewModel @Inject constructor(
    private val specificProductUseCase: GetSpecificProductUseCase
) : ViewModel()
{
    private val _specificProductState = MutableStateFlow(SpecProdScreenState())
    val specificProductState = _specificProductState.asStateFlow()

    fun getSpecificProduct(productId: String) {
        viewModelScope.launch {
            _specificProductState.update {
                it.copy(isLoading = true, error = null)
            }

            specificProductUseCase(productId).collect { apiOperation ->
                apiOperation.onSuccess { product ->
                    _specificProductState.update {
                        it.copy(
                            isLoading = false,
                            product = product
                        )
                    }
                }
                    .onFailure { exception ->
                        _specificProductState.update {
                            it.copy(isLoading = false, error = exception.message)
                        }
                    }
            }
        }
    }
}