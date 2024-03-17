package com.dshagapps.tfi_software.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dshagapps.tfi_software.data.repositories.SaleRepositoryImpl
import com.dshagapps.tfi_software.data.service.ServiceProvider
import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.domain.repositories.SaleRepository
import com.dshagapps.tfi_software.presentation.navigation.Navigation
import com.dshagapps.tfi_software.presentation.ui.theme.TfisoftwareTheme
import com.dshagapps.tfi_software.presentation.viewmodel.SaleViewModel

class MainActivity : BaseActivity() {

    private lateinit var repository: SaleRepository

    private val viewModel: SaleViewModel by viewModel {
        SaleViewModel(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = SaleRepositoryImpl(ServiceProvider(applicationContext).createService(Api::class.java))

        setContent {
            TfisoftwareTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        viewModel = viewModel,
                        onBack = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}