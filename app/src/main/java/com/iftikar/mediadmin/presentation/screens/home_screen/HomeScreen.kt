package com.iftikar.mediadmin.presentation.screens.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.iftikar.mediadmin.R
import com.iftikar.mediadmin.navigation.Routes

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HomeScreenItem(
                        image = R.drawable.users,
                        text = "Users",
                        onClick = {
                            navHostController.navigate(Routes.UsersScreen)
                        }
                    )

                    HomeScreenItem(
                        image = R.drawable.products,
                        text = "Products",
                        onClick = {
                            navHostController.navigate(Routes.AllProductsScreen)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeScreenItem(
    image: Int,
    text: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.clickable(
            onClick = onClick
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            else MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = image,
                contentDescription = text,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


