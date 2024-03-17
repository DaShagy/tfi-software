package com.dshagapps.tfi_software.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.presentation.models.StockUiModel
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartSaleScreen(
    modifier: Modifier = Modifier,
    branchId: Int = 1,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    viewModel: SaleViewModel
) {

    BackHandler(onBack = onBack)

    var search by remember { mutableStateOf("") }

    val stockList = viewModel.filteredStockList.collectAsState()

    val saleLines = viewModel.saleLines.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = search,
            onValueChange = {
                search = it
                viewModel.onFilterChange(it)
            },
            maxLines = 1,
            label = { Text("Buscar") }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1.0f)
                .padding(vertical = 8.dp)
        ) {
            this.items(stockList.value) { stock ->
                StockCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    stock = stock,
                    onAddProduct = {
                        viewModel.addProductToSale(stock)
                    },
                    onRemoveProduct = {
                        viewModel.removeProductFromSale(stock)
                    }
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onContinue,
            enabled = saleLines.value.isNotEmpty()
        ) {
            Text("Siguiente")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getStockByBranch(branchId)
    }
}

@Composable
private fun StockCard(
    modifier: Modifier = Modifier,
    stock: StockUiModel,
    onAddProduct: () -> Unit,
    onRemoveProduct: () -> Unit
) {
    Card(
        modifier = modifier
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            StyledText(
                title = "Prenda:",
                description = stock.productDescription
            )
            StyledText(
                title = "Color:",
                description = stock.colorDescription
            )

            StyledText(
                title = "Talle:",
                description = stock.sizeDescription
            )

            StyledText(
                title = "Precio:",
                description = "$${DecimalFormat("#.00").format(stock.price)}"
            )

            StyledText(
                title = "${stock.quantity}:",
                description = "${stock.maxQuantity}"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onAddProduct
                ) {
                    Text(text = "Agregar a la venta")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onRemoveProduct
                ) {
                    Text(text = "Quitar de la venta" )
                }
            }
        }
    }
}


@Composable
private fun StyledText(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {

    val combinedText = buildAnnotatedString {
        append(title)
        append(" ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(description)
        }
    }

    Text(
        modifier = modifier.fillMaxWidth(),
        text = combinedText
    )
}