package com.dshagapps.tfi_software.presentation.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.presentation.ui.components.ScreenBottomButtons

@Composable
fun SaleResultScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.weight(1.0f))

        ScreenBottomButtons(
            onPrimaryButton = onBack,
        )
    }

    LaunchedEffect(Unit) {
        Toast.makeText(context, "VENTA EXITOSA", Toast.LENGTH_SHORT).show()
    }
}