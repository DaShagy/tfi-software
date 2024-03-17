package com.dshagapps.tfi_software.presentation.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.presentation.ui.components.ScreenBottomButtons
import com.dshagapps.tfi_software.presentation.utils.getTotal
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: SaleViewModel
) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current

    val saleLines = viewModel.saleLines.collectAsState()

    val isFinalConsumerEnabled = saleLines.value.getTotal() < 1000

    var isAnonymousClient by remember { mutableStateOf(isFinalConsumerEnabled) }

    var cuit by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isFinalConsumerEnabled) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = if (isFinalConsumerEnabled) isAnonymousClient else false,
                    onCheckedChange = { isAnonymousClient = !isAnonymousClient },
                    enabled = isFinalConsumerEnabled
                )
                Text(
                    text = "Es cliente anónimo"
                )
            }
        }

        if (!isAnonymousClient) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1.0f),
                    value = cuit,
                    onValueChange = { cuit = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1
                )

                Button(onClick = { Toast.makeText(context, cuit, Toast.LENGTH_SHORT).show() }) {
                    Text("Buscar CUIT")
                }
            }
        }

        Spacer(modifier = modifier.weight(1.0f))

        ScreenBottomButtons(
            onPrimaryButton = onBack,
            onSecondaryButton = onBack,
            primaryButtonEnabled = false
        )
    }
}