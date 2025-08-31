package com.iftikar.mediadmin.presentation.screens.products_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediadmin.domain.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductsViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {
    private val _allProductsState = MutableStateFlow(AllProductsState())
    val allProductsState = _allProductsState.asStateFlow()

    private val _eventState: Channel<AllProductsEvent> = Channel()
    val eventState = _eventState.receiveAsFlow()

    init {
        getAllProducts()
    }

    fun onEvent(event: AllProductsEvent) {
        when(event) {
            is AllProductsEvent.GoToSpecificProduct -> {
                viewModelScope.launch {
                    _eventState.send(AllProductsEvent.GoToSpecificProduct(event.productId))
                }
            }

            AllProductsEvent.GoToAddProductScreen -> {
                viewModelScope.launch {
                    _eventState.send(AllProductsEvent.GoToAddProductScreen)
                }
            }

            AllProductsEvent.Refresh -> getAllProducts()

            is AllProductsEvent.ToggleCategorizedSwitch -> {
                _allProductsState.update {
                    it.copy(isCategorized = event.checked)
                }
            }
            else -> Unit
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _allProductsState.update {
                it.copy(isLoading = true, error = null)
            }

            getAllProductsUseCase().collect { apiOperation ->
                apiOperation.onSuccess { products ->
                    Log.d("Products", "getAllProducts: $products")
                    _allProductsState.update {
                        it.copy(
                            isLoading = false,
                            products = products
                        )
                    }
                }.onFailure { exception ->
                    Log.d("Products", "getAllProducts: $exception")
                    _allProductsState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                }
            }
        }
    }

}






















