package com.dshagapps.tfi_software.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.presentation.ui.components.ScreenBottomButtons
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPaymentFormScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    clientFullName: String,
    viewModel: SaleViewModel
) {
    BackHandler(onBack = onBack)

    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf(clientFullName) }
    var cardExpirationMonth by remember { mutableStateOf("") }
    var cardExpirationYear by remember { mutableStateOf("") }
    var cardCcv by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = cardNumber,
                onValueChange = { if (it.length <= 16) cardNumber = it },
                label = { Text("Tarjeta") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1
            )
        }

        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = cardHolder,
                onValueChange = { cardHolder = it },
                label = { Text("Titular") },
                maxLines = 1,
            )
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = modifier.weight(1.0f),
                value = cardExpirationMonth,
                onValueChange = {
                    if (it.length <= 2) cardExpirationMonth = if(it.isNotEmpty() && it.toInt() > 12) "12" else it
                },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
            )
            OutlinedTextField(
                modifier = modifier.weight(1.0f),
                value = cardExpirationYear,
                onValueChange = { if (it.length <= 2) cardExpirationYear = it },
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
            )
            OutlinedTextField(
                modifier = modifier.weight(1.0f),
                value = cardCcv,
                onValueChange = { if (it.length <= 3) cardCcv = it },
                label = { Text("CCV") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
            )
        }

        Spacer(
            modifier = Modifier.weight(1.0f)
        )

        ScreenBottomButtons(
            onPrimaryButton = {
                viewModel.startSale(
                    cardNumber = cardNumber,
                    cardHolder = cardHolder,
                    cardExpiry = "$cardExpirationMonth$cardExpirationYear",
                    cardCcv = cardCcv,
                    amount = "100000"
                )
            },
            onSecondaryButton = onBack,
        )
    }
}