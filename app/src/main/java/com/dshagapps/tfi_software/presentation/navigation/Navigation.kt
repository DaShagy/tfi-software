package com.dshagapps.tfi_software.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.presentation.screen.MainScreen
import com.dshagapps.tfi_software.presentation.screen.StartSaleScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    api: Api
) {
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            MainScreen(
                onStartSale = { branchId ->
                    navController.navigate("startSaleScreen/$branchId")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "startSaleScreen/{branchId}",
            arguments = listOf(navArgument("branchId") { type = NavType.IntType })
        ) {
            StartSaleScreen(
                branchId = it.arguments?.getInt("branchId") ?: 1,
                onBack = {
                    navController.popBackStack()
                },
                api = api
            )
        }
    }
}