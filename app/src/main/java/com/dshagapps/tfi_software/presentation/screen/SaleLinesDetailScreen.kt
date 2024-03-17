package com.dshagapps.tfi_software.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SaleLinesDetailScreen(
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    Text(text = "Hola mundo")
}