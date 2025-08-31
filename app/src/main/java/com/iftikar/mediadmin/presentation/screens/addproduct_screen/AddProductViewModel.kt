package com.iftikar.mediadmin.presentation.screens.addproduct_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iftikar.mediadmin.data.remote.model.ProductRequestDto
import com.iftikar.mediadmin.domain.usecase.InsertProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val insertProductUseCase: InsertProductUseCase
) : ViewModel() {
    private val _insertProductState = MutableStateFlow(AddProductScreenState())
    val insertProductState = _insertProductState.asStateFlow()

    fun onEvent(event: AddProductScreenEvent) {
        when(event) {
            is AddProductScreenEvent.OnCategoryChange -> {
                _insertProductState.update {
                    it.copy(category = event.category)
                }
            }
            is AddProductScreenEvent.OnNameChange -> {
                _insertProductState.update {
                    it.copy(name = event.name)
                }
            }
            is AddProductScreenEvent.OnPriceChange -> {
                _insertProductState.update {
                    it.copy(price = event.price)
                }
            }
            is AddProductScreenEvent.OnStockChange -> {
                _insertProductState.update {
                    it.copy(stock = event.stock)
                }
            }
            AddProductScreenEvent.InsertProduct -> insertProduct()
        }
    }

    private fun insertProduct() {
        viewModelScope.launch {
            _insertProductState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            insertProductUseCase(
                productRequestDto = ProductRequestDto(
                    name = _insertProductState.value.name,
                    category = _insertProductState.value.category,
                    price = _insertProductState.value.price.toDoubleOrNull() ?: 0.0,
                    stock = _insertProductState.value.stock.toInt()
                )
            ).collect { apiOperation ->
                apiOperation.onSuccess { (message, status) ->
                    if (status == 200) {
                        _insertProductState.update {
                            it.copy(
                                isLoading = false,
                                successMessage = "Product inserted, you can add another product or go back",
                                name = "",
                                category = "",
                                price = "",
                                stock = ""
                            )
                        }
                    } else {
                        Log.e("Insert Product", message, )
                        _insertProductState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Product could not be added!"
                            )
                        }
                    }
                }.onFailure {exception ->
                    _insertProductState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message
                        )
                    }
                }
            }
        }
    }
}



















