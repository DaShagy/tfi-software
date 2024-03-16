package com.dshagapps.tfi_software.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.repositories.SaleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SaleViewModel(
    private val repository: SaleRepository
): ViewModel() {

    var stockList: MutableStateFlow<List<Stock>> = MutableStateFlow(emptyList())

    fun getStockByBranch(branchId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getStockByBranchId(branchId).fold(
            onSuccess = {
                stockList.value = it
            },
            onFailure = {
                stockList.value = emptyList()
            }
        )
    }
}