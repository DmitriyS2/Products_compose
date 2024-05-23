package com.sd.products.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.products.presentation.ui.screen.CurrentItem
import com.sd.products.presentation.ui.screen.MainScreen
import com.sd.products.presentation.ui.theme.ProductsTheme
import com.sd.products.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductsTheme {
                val navController = rememberNavController()
                val vm: MainViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = Routes.Main.route
                ) {
                    composable(Routes.Main.route) {
                        MainScreen(vm, navController)
                    }
                    composable(Routes.Current.route) {
                        CurrentItem(vm, navController)
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    object Main : Routes("mainScreen")
    object Current : Routes("currentItem")
}
