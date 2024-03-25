package com.dshagapps.tfi_software.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dshagapps.tfi_software.presentation.screen.AuthScreen
import com.dshagapps.tfi_software.presentation.screen.CardPaymentFormScreen
import com.dshagapps.tfi_software.presentation.screen.ClientDetailScreen
import com.dshagapps.tfi_software.presentation.screen.MainScreen
import com.dshagapps.tfi_software.presentation.screen.SaleLinesDetailScreen
import com.dshagapps.tfi_software.presentation.screen.SaleResultScreen
import com.dshagapps.tfi_software.presentation.screen.StockDetailsScreen
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    viewModel: SaleViewModel,
    onBack: () -> Unit
) {
    NavHost(navController = navController, startDestination = "authScreen") {
        composable("authScreen") {
            AuthScreen(
                onLogin = { salesmenId ->
                    navController.navigate("mainScreen?salesmenId=$salesmenId")
                    Log.d("SALESMENID", salesmenId.toString())
                },
                onBack = {
                    if (!navController.popBackStack()) onBack()
                },
                viewModel = viewModel
            )
        }

        composable(
            route = "mainScreen?salesmenId={salesmenId}",
            arguments = listOf(navArgument("salesmenId") { type = NavType.IntType })
        ) {
            MainScreen(
                onStartSale = {
                    val salesmenId = it.arguments?.getInt("salesmenId", 0) ?: 0
                    Log.d("SALESMENID", salesmenId.toString())
                    navController.navigate("stockDetailsScreen?salesmenId=$salesmenId")
                },
                onBack = {
                    if (!navController.popBackStack()) onBack()
                },
            )
        }

        composable(
            route = "stockDetailsScreen?salesmenId={salesmenId}",
            arguments = listOf(navArgument("salesmenId") { type = NavType.IntType })
        ) {
            StockDetailsScreen(
                salesmenId = it.arguments?.getInt("salesmenId") ?: 1,
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
                onContinue = { clientName, cuit ->
                    navController.navigate("cardPaymentFormScreen?clientName=$clientName&cuit=$cuit")
                },
                onSale = {
                    navController.navigate("saleResultScreen")
                },
                viewModel = viewModel
            )
        }

        composable(
            route = "cardPaymentFormScreen?clientName={clientName}&cuit={cuit}",
            arguments = listOf(
                navArgument("clientName") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("cuit") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            CardPaymentFormScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSale = {
                    navController.navigate("saleResultScreen")
                },
                viewModel = viewModel,
                clientFullName = it.arguments?.getString("clientName") ?: "",
                clientCuit = it.arguments?.getString("cuit") ?: ""
            )
        }

        composable("saleResultScreen") {
            SaleResultScreen {
                navController.popBackStack("mainScreen", false)
                viewModel.cleanStates()
            }
        }
    }
}