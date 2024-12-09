package com.radlab.nbpapi.ui.screens

import CurrencyDetailsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.radlab.nbpapi.viewModel.CurrencyViewModel

@Composable
fun MainScreen(viewModel: CurrencyViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "currencyList"
    ) {
        addCurrencyListComposable(navController, viewModel)
        addCurrencyDetailsComposable(navController, viewModel)
    }
}

private fun NavGraphBuilder.addCurrencyListComposable(
    navController: NavController,
    viewModel: CurrencyViewModel
) {
    composable("currencyList") {
        CurrencyListScreen(
            viewModel = viewModel,
            onCurrencyClick = { currencyCode, tableCode ->
                navController.navigate("currencyDetails/$tableCode/$currencyCode")
            }
        )
    }
}

private fun NavGraphBuilder.addCurrencyDetailsComposable(
    navController: NavController,
    viewModel: CurrencyViewModel
) {
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
