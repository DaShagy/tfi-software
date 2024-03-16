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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.data.service.response.StockResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StartSaleScreen(
    modifier: Modifier = Modifier,
    branchId: Int = 1,
    onBack: () -> Unit,
    api: Api
) {

    BackHandler(onBack = onBack)

    var list by remember { mutableStateOf<List<StockResponse>>(emptyList()) }

    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(8.dp)
    ) {
        this.items(list) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "${it.id}")
                Text(text = it.articulo)
                Text(text = it.color)
                Text(text = it.talle)
                Text(text = "Cantidad: ${it.cantidad}")
            }
        }
    }

    LaunchedEffect(Unit) {
        this.launch(Dispatchers.IO) {
            api.getStock(branchId).execute().also {
                list = it.body()?.content?.toList() ?: emptyList()
            }
        }
    }
}