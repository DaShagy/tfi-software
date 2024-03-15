package com.dshagapps.tfi_software.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.data.service.ServiceProvider
import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.data.service.response.StockResponse
import com.dshagapps.tfi_software.presentation.ui.TfisoftwareTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var api: Api

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = ServiceProvider(applicationContext).createService(Api::class.java)

        setContent {

            val scope = rememberCoroutineScope()
            
            var branchId by remember { mutableStateOf("") }

            var list by remember { mutableStateOf<List<StockResponse>>(emptyList()) }

            TfisoftwareTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = branchId,
                                onValueChange = { branchId = it },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                            )


                            Button(onClick = {
                                scope.launch(Dispatchers.IO) {
                                    api.getStock(branchId.toIntOrNull() ?: 1).execute().also {
                                        list = it.body()?.content?.toList() ?: emptyList()
                                    }
                                }
                            }) {
                                Text(text = "get stock")
                            }
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            this.items(list) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp),
                                    horizontalArrangement = SpaceBetween
                                ) {
                                    Text(text = "${it.id}")
                                    Text(text = it.articulo)
                                    Text(text = it.color)
                                    Text(text = it.talle)
                                    Text(text = "Cantidad: ${it.cantidad}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}