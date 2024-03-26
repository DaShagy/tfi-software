package com.dshagapps.tfi_software.presentation.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.presentation.ui.components.ScreenBottomButtons
import com.dshagapps.tfi_software.presentation.utils.toPriceString
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel
import kotlinx.coroutines.launch

@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: SaleViewModel
) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    val receipt = viewModel.receipt.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .scrollable(
                state = rememberScrollableState(consumeScrollDelta = { 0f }),
                orientation = Orientation.Vertical,
                enabled = true,
                reverseDirection = false
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        receipt?.let {
            Text(it.receiptType)
            Text("FACTURA")
            Text("Punto de venta: ${it.salePoint}")
            Text("Vendedor: ${it.salesmanName}")
            Text("Comp. Nro.: ${it.saleNumber}")
            Text("CUIT: ${it.businessCuit}")
            Text("Domicilio: ${it.businessAddress}")
            Text("Condición frente al IVA: ${it.businessTributeCondition}")
            Text("Total Neto Gravado: ${it.saleTaxedAmount.toDouble().toPriceString()}")
            Text("Total IVA: ${it.saleTaxAmount.toDouble().toPriceString()}")
            Text("Total: ${it.saleTotalAmount.toDouble().toPriceString()}")
            Text("Nro CAE: ${it.caeNumber}")
            Text("Vencimiento CAE: ${it.caeExpirationDate}")
            Text("Fecha de emisión: ${it.issueDate}")
        }

        ScreenBottomButtons(
            onPrimaryButton = {
                viewModel.logout(
                    onSuccessCallback = {
                        coroutineScope.launch {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            onBack()
                        }
                    },
                    onFailureCallback = {
                        coroutineScope.launch {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            onBack()
                        }
                    }
                )
            }
        )
    }
}