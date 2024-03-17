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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.presentation.models.ClientUiModel
import com.dshagapps.tfi_software.presentation.ui.components.ScreenBottomButtons
import com.dshagapps.tfi_software.presentation.utils.getTotal
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: SaleViewModel
) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var client by remember { mutableStateOf(ClientUiModel()) }

    val isFinalConsumerEnabled = viewModel.saleLines.collectAsState().value.getTotal() < 1000

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

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.getClientByCuit(cuit) {
                                this.launch {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }.collect { client = it }
                        }
                    }
                ) {
                    Text("Buscar CUIT")
                }
            }

            if (client.firstName.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(text = "Nombre: ${client.firstName}")
                    Text(text = "Apellido: ${client.lastName}")
                    Text(text = "Domicilio: ${client.address}")
                    Text(text = "CUIT: ${client.cuit}")
                    Text(text = "Condición Tributaria: ${client.tributeCondition}")
                }
            }
        }

        Spacer(modifier = modifier.weight(1.0f))

        ScreenBottomButtons(
            onPrimaryButton = onBack,
            onSecondaryButton = onBack,
            primaryButtonEnabled = isAnonymousClient || client != ClientUiModel()
        )
    }
}
