package com.dshagapps.tfi_software.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.repositories.SaleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SaleViewModel(
    private val repository: SaleRepository
): ViewModel() {

    var stockList: MutableStateFlow<List<Stock>> = MutableStateFlow(emptyList())
        private set

    var filteredStockList: MutableStateFlow<List<Stock>> = MutableStateFlow(emptyList())
        private set

    private val filteredTextFlow: MutableStateFlow<String> = MutableStateFlow("")

    init {
        combine(stockList, filteredTextFlow) { stocks, filterText ->
            stocks.filter { stock ->
                stock.productDescription.contains(filterText, ignoreCase = true)
                        || stock.colorDescription.contains(filterText, ignoreCase = true)
                        || stock.sizeDescription.contains(filterText, ignoreCase = true)
                        || filterText.toIntOrNull()?.equals(stock.productId) == true
            }
        }
            .distinctUntilChanged()
            .onEach { filteredStocks ->
                filteredStockList.value = filteredStocks
            }
            .launchIn(viewModelScope)
    }

    fun onFilterChange(filterText: String) {
        viewModelScope.launch {
            filteredTextFlow.value = filterText
        }
    }

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