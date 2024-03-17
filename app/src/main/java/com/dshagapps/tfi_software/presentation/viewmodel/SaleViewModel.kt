package com.dshagapps.tfi_software.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.repositories.SaleRepository
import com.dshagapps.tfi_software.presentation.models.SaleLineUiModel
import com.dshagapps.tfi_software.presentation.models.StockUiModel
import com.dshagapps.tfi_software.presentation.utils.decrementStockQuantity
import com.dshagapps.tfi_software.presentation.utils.incrementStockQuantity
import com.dshagapps.tfi_software.presentation.utils.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SaleViewModel(
    private val repository: SaleRepository
) : ViewModel() {

    private var stockList: MutableStateFlow<List<Stock>> = MutableStateFlow(emptyList())

    var filteredStockList: MutableStateFlow<List<StockUiModel>> = MutableStateFlow(emptyList())
        private set

    private val filteredTextFlow: MutableStateFlow<String> = MutableStateFlow("")

    val saleLines: MutableStateFlow<List<SaleLineUiModel>> = MutableStateFlow(emptyList())

    init {
        combine(stockList, filteredTextFlow) { stocks, filterText ->
            val keywords = filterText.split(" ")
            stocks.filter { stock ->
                keywords.any { keyword ->
                    stock.productDescription.contains(keyword, ignoreCase = true)
                            || stock.colorDescription.contains(keyword, ignoreCase = true)
                            || stock.sizeDescription.contains(keyword, ignoreCase = true)
                            || keyword.toIntOrNull()?.equals(stock.productId) == true
                }
            }
        }
            .distinctUntilChanged()
            .onEach { filteredStocks ->
                filteredStockList.value = filteredStocks.map { stock -> stock.toUiModel() }
                updateFilteredStockList()
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

    fun addProductToSale(stock: StockUiModel) {
        val existingSaleLine = saleLines.value.find { line -> line.stock.id == stock.id }

        if (existingSaleLine == null) {
            val newSaleLine = SaleLineUiModel(
                id = saleLines.value.size + 1,
                stock = stock.copy(quantity = 1),
            )
            saleLines.value = saleLines.value + newSaleLine
        } else {
            val updatedSaleLines = saleLines.value.map { line ->
                if (line.id == existingSaleLine.id) {
                    line.incrementStockQuantity()
                } else {
                    line
                }
            }
            saleLines.value = updatedSaleLines
        }

        updateFilteredStockList()
    }

    fun removeProductFromSale(stock: StockUiModel) {
        val existingSaleLine = saleLines.value.find { line -> line.stock.id == stock.id }

        if (existingSaleLine != null) {
            val updatedSaleLines = saleLines.value.map { line ->
                if (line.id == existingSaleLine.id) {
                    line.decrementStockQuantity()
                } else {
                    line
                }
            }
            saleLines.value = updatedSaleLines.filterNot { line -> line.stock.quantity == 0 }
        }

        updateFilteredStockList()
    }

    private fun updateFilteredStockList() {
        filteredStockList.value = filteredStockList.value.map {  stock ->
            saleLines.value.find { line -> line.stock.id == stock.id }?.stock ?: stock.copy(quantity = 0)
        }
    }
}