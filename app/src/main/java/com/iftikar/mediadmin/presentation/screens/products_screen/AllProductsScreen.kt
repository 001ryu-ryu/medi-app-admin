package com.iftikar.mediadmin.presentation.screens.products_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iftikar.mediadmin.domain.model.Product
import com.iftikar.mediadmin.ui.theme.Green40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllProductsScreen(
    viewModel: AllProductsViewModel,
    onNavigate: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val state = viewModel.allProductsState.collectAsStateWithLifecycle()
    val eventState by viewModel.eventState.collectAsState(AllProductsEvent.Idle)
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val onEvent = viewModel::onEvent

    LaunchedEffect(eventState) {
        when (val event = eventState) {
            is AllProductsEvent.GoToSpecificProduct -> {
                onNavigate(event.productId)
            }

            AllProductsEvent.GoToAddProductScreen -> {
                onAddClick()
            }

            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        onEvent(AllProductsEvent.Refresh)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            IconButton(
                onClick = {
                    onEvent(AllProductsEvent.GoToAddProductScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {Text("Products")},
                actions = {
                    ModeToggle(
                        checked = state.value.isCategorized,
                        onCheckedChange = { onEvent(AllProductsEvent.ToggleCategorizedSwitch(it)) }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
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


            state.value.products.isNotEmpty() -> {
                when (state.value.isCategorized) {
                    true -> {
                        CategorizedList(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            products = state.value.products,
                            listState = listState,
                            onClick = {
                                onEvent(AllProductsEvent.GoToSpecificProduct(it))
                            }
                        )
                    }

                    false -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding),
                            state = listState
                        ) {
                            items(
                                items = state.value.products
                            ) { product ->
                                ProductItem(
                                    modifier = Modifier.padding(horizontal = 6.dp),
                                    product = product,
                                    onClick = {
                                        onEvent(AllProductsEvent.GoToSpecificProduct(it))
                                    }
                                )
                            }
                        }
                    }
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
        }
    }
}

@Composable
private fun ModeToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Categorize")
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            modifier = Modifier.scale(0.7f)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategorizedList(
    modifier: Modifier,
    products: List<Product>,
    listState: LazyListState,
    onClick: (String) -> Unit
) {
    val categorizedList = products.groupBy {
        it.category.uppercase()
    }.toSortedMap()
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState
    ) {
        categorizedList.forEach { (key, value) ->
            stickyHeader {
                CategoryHeader(
                    text = key
                )
            }
            items(items = value) { product ->
                ProductItem(
                    product = product,
                    onClick = { onClick(it) },
                )
            }
        }
    }
}

@Composable
private fun CategoryHeader(
    text: String
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    )
}

@Composable
private fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (String) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .padding(bottom = 6.dp),
        onClick = { onClick(product.productId) }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                color = Green40
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Category: ${product.category}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "In stock: ${product.stock}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "$" + product.price,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Green
                )
            }
        }
    }
}
















