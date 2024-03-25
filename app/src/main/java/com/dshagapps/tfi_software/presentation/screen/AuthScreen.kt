package com.dshagapps.tfi_software.presentation.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    onLogin: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: SaleViewModel
) {
    BackHandler (onBack = onBack)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = user,
            onValueChange = { user = it },
            maxLines = 1,
            label = {
                Text(text = "User")
            }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = pass,
            onValueChange = { pass = it },
            maxLines = 1,
            label = {
                Text(text = "Password")
            }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.login(
                    user,
                    pass,
                    onSuccessCallback = {
                        coroutineScope.launch {
                            onLogin(it)
                        }
                    },
                    onFailureCallback = {
                        coroutineScope.launch {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        ) {
            Text(text = "Iniciar sesión")
        }
    }
}