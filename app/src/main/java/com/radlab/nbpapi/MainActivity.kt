package com.radlab.nbpapi

import CurrencyDetailsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.radlab.nbpapi.ui.screens.CurrencyListScreen
import com.radlab.nbpapi.ui.theme.NBPApiTheme
import com.radlab.nbpapi.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val currencyViewModel: CurrencyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBPApiTheme {
                MainScreen(viewModel = currencyViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: CurrencyViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "currencyList"
    ) {
        composable("currencyList") {
            CurrencyListScreen(
                viewModel = viewModel,
                onCurrencyClick = { currencyCode, tableCode ->
                    navController.navigate("currencyDetails/$tableCode/$currencyCode")
                }
            )
        }

        composable(
            "currencyDetails/{tableCode}/{currencyCode}",
            arguments = listOf(
                navArgument("tableCode") { type = NavType.StringType },
                navArgument("currencyCode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tableCode = backStackEntry.arguments?.getString("tableCode") ?: ""
            val currencyCode = backStackEntry.arguments?.getString("currencyCode") ?: ""
            CurrencyDetailsScreen(
                table = tableCode,
                currencyCode = currencyCode,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
