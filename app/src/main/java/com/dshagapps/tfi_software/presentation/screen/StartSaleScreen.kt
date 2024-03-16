package com.dshagapps.tfi_software.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel

@Composable
fun StartSaleScreen(
    modifier: Modifier = Modifier,
    branchId: Int = 1,
    onBack: () -> Unit,
    viewModel: SaleViewModel
) {

    BackHandler(onBack = onBack)

    val stockList = viewModel.stockList.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        this.items(stockList.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "${it.id}")
                Text(text = it.productDescription)
                Text(text = it.colorDescription)
                Text(text = it.sizeDescription)
                Text(text = "Cantidad: ${it.quantity}")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getStockByBranch(branchId)
    }
}