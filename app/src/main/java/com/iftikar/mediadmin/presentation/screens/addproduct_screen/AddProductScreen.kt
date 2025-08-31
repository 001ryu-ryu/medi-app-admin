package com.iftikar.mediadmin.presentation.screens.addproduct_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel
) {
    val state = viewModel.insertProductState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.value.name,
                onValueChange = { onEvent(AddProductScreenEvent.OnNameChange(it)) },
                label = { Text("product name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 12.dp)
            )

            TextField(
                value = state.value.category,
                onValueChange = { onEvent(AddProductScreenEvent.OnCategoryChange(it)) },
                label = { Text("product category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 12.dp)
            )

            TextField(
                value = state.value.price,
                onValueChange = { onEvent(AddProductScreenEvent.OnPriceChange(it)) },
                label = { Text("product price") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                value = state.value.stock,
                onValueChange = { input ->
                    if (input.all { it.isDigit() }) {
                        onEvent(AddProductScreenEvent.OnStockChange(input)) }
                    },
                label = { Text("number of products") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { onEvent(AddProductScreenEvent.InsertProduct) },
                enabled = state.value.name.isNotEmpty() && state.value.category.isNotEmpty()
                        && state.value.price.isNotEmpty() && state.value.stock.isNotEmpty()
                        && !state.value.price.contains("..")
            ) {
                Text(
                    "Insert",
                    color = Color.White
                )
            }

            when {
                state.value.isLoading -> {
                    Text("Inserting...")
                }

                state.value.successMessage.isNotEmpty() -> {
                    Text(state.value.successMessage)
                }

                state.value.errorMessage != null -> {
                    val error = state.value.errorMessage
                    if (error != null) {
                        Text(error)
                    }
                }
            }
        }
    }
}





















