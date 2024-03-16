package com.dshagapps.tfi_software.presentation.screen

import android.content.res.Resources.Theme
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartSaleScreen(
    modifier: Modifier = Modifier,
    branchId: Int = 1,
    onBack: () -> Unit,
    viewModel: SaleViewModel
) {

    BackHandler(onBack = onBack)

    var search by remember { mutableStateOf("") }

    val stockList = viewModel.filteredStockList.collectAsState()

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
            label = {
                Text("Buscar")
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            this.items(stockList.value) {
                StockCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    stock = it
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getStockByBranch(branchId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockCard(
    modifier: Modifier = Modifier,
    stock: Stock,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ){
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            StyledText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                title = "Prenda:",
                description = stock.productDescription
            )
            StyledText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                title = "Color:",
                description = stock.colorDescription
            )

            StyledText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                title = "Talle:",
                description = stock.sizeDescription
            )

            StyledText(
                modifier = Modifier.fillMaxWidth(),
                title = "Precio:",
                description = "$${DecimalFormat("#.00").format(stock.price)}"
            )
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
        modifier = modifier,
        text = combinedText
    )
}