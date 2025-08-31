package com.iftikar.mediadmin.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.iftikar.mediadmin.presentation.screens.addproduct_screen.AddProductScreen
import com.iftikar.mediadmin.presentation.screens.addproduct_screen.AddProductViewModel
import com.iftikar.mediadmin.presentation.screens.home_screen.HomeScreen
import com.iftikar.mediadmin.presentation.screens.products_screen.AllProductsScreen
import com.iftikar.mediadmin.presentation.screens.products_screen.AllProductsViewModel
import com.iftikar.mediadmin.presentation.screens.products_screen.SpecificProductScreen
import com.iftikar.mediadmin.presentation.screens.products_screen.SpecificProductViewModel
import com.iftikar.mediadmin.presentation.screens.users_screen.UsersScreen

@Composable
fun NavApp() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.HomeScreen) {

        composable<Routes.HomeScreen> {
            HomeScreen(navHostController = navHostController)
        }

        composable<Routes.UsersScreen> {
            UsersScreen()
        }

        composable<Routes.AllProductsScreen> {
            val viewModel = hiltViewModel<AllProductsViewModel>()
            AllProductsScreen(
                viewModel = viewModel,
                onNavigate = { navHostController.navigate(Routes.SpecificProductScreen(it)) },
                onAddClick = {navHostController.navigate(Routes.AddProductScreen)},
            )
        }

        composable<Routes.SpecificProductScreen> {
            val viewModel = hiltViewModel<SpecificProductViewModel>()
            val productId = it.toRoute<Routes.SpecificProductScreen>().productId
            SpecificProductScreen(
                viewModel = viewModel,
                productId = productId
            )
        }

        composable<Routes.AddProductScreen> {
            val viewModel = hiltViewModel<AddProductViewModel>()
            AddProductScreen(viewModel)
        }
    }
}


















