package com.iftikar.mediadmin.presentation.screens.products_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iftikar.mediadmin.domain.model.Product
import com.iftikar.mediadmin.ui.theme.Lime80
import com.iftikar.mediadmin.ui.theme.Teal80

@Composable
fun SpecificProductScreen(
    viewModel: SpecificProductViewModel,
    productId: String
) {

    val state = viewModel.specificProductState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getSpecificProduct(productId)
    }

    Scaffold { innerPadding ->
        when {
            state.value.isLoading -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading product...")
                }
            }

            state.value.error != null -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error: ${state.value.error}",
                        color = Color.Red
                    )
                }
            }

            state.value.product != null -> {
                val product = state.value.product
                if (product != null) {
                    ProductInDetail(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        product = product
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductInDetail(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedCard(
                elevation = CardDefaults.elevatedCardElevation(12.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Lime80
                )
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Text(
                text = "$" + product.price.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Green
            )
        }

        Spacer(Modifier.height(50.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Teal80, shape = CircleShape)
            )
            Text(
                text = "Category: ${product.category}",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Teal80, shape = CircleShape)
            )
            Text(
                text = "Items Left: ${product.stock}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
















