package com.dshagapps.tfi_software.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dshagapps.tfi_software.presentation.screen.CardPaymentFormScreen
import com.dshagapps.tfi_software.presentation.screen.ClientDetailScreen
import com.dshagapps.tfi_software.presentation.screen.MainScreen
import com.dshagapps.tfi_software.presentation.screen.SaleLinesDetailScreen
import com.dshagapps.tfi_software.presentation.screen.StartSaleScreen
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    viewModel: SaleViewModel,
    onBack: () -> Unit
) {
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            MainScreen(
                onStartSale = { branchId ->
                    navController.navigate("startSaleScreen?branchId=$branchId")
                },
                onBack = {
                    if (!navController.popBackStack()) onBack()
                }
            )
        }

        composable(
            route = "startSaleScreen?branchId={branchId}",
            arguments = listOf(navArgument("branchId") { type = NavType.IntType })
        ) {
            StartSaleScreen(
                branchId = it.arguments?.getInt("branchId") ?: 1,
                onBack = {
                    navController.popBackStack()
                    viewModel.cleanStates()
                },
                onContinue = {
                    navController.navigate("saleLinesDetailScreen")
                },
                viewModel = viewModel
            )
        }

        composable("saleLinesDetailScreen") {
            SaleLinesDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onContinue = {
                    navController.navigate("clientDetailScreen")
                },
                viewModel = viewModel
            )
        }

        composable("clientDetailScreen") {
            ClientDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onContinue = { clientName ->
                    navController.navigate("cardPaymentFormScreen?clientName=${clientName}")
                },
                viewModel = viewModel
            )
        }

        composable(
            route = "cardPaymentFormScreen?clientName={clientName}",
            arguments = listOf(navArgument("clientName") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            CardPaymentFormScreen(
                onBack = {
                    navController.popBackStack()
                },
                viewModel = viewModel,
                clientFullName = it.arguments?.getString("clientName") ?: ""
            )
        }
    }
}