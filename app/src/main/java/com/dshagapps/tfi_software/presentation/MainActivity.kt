package com.dshagapps.tfi_software.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dshagapps.tfi_software.data.service.ServiceProvider
import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.presentation.navigation.Navigation
import com.dshagapps.tfi_software.presentation.ui.TfisoftwareTheme

class MainActivity : ComponentActivity() {

    private lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = ServiceProvider(applicationContext).createService(Api::class.java)

        setContent {
            TfisoftwareTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(api = api)
                }
            }
        }
    }
}