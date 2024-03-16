package com.dshagapps.tfi_software.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                onStartSale = {
                    navController.navigate("startSaleScreen")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("startSaleScreen") {
            StartSaleScreen(
                onBack = {
                    navController.popBackStack()
                },
                api = api
            )
        }
    }
}