package com.dshagapps.tfi_software.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dshagapps.tfi_software.domain.entities.Card
import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.domain.entities.Sale
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.enums.PaymentType
import com.dshagapps.tfi_software.domain.repositories.SaleRepository
import com.dshagapps.tfi_software.domain.rules.AnonymousClientRules
import com.dshagapps.tfi_software.presentation.models.ClientUiModel
import com.dshagapps.tfi_software.presentation.models.SaleLineUiModel
import com.dshagapps.tfi_software.presentation.models.StockUiModel
import com.dshagapps.tfi_software.presentation.utils.decrementStockQuantity
import com.dshagapps.tfi_software.presentation.utils.formatWithoutDecimalSeparator
import com.dshagapps.tfi_software.presentation.utils.getTotal
import com.dshagapps.tfi_software.presentation.utils.incrementStockQuantity
import com.dshagapps.tfi_software.presentation.utils.toDomainEntity
import com.dshagapps.tfi_software.presentation.utils.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigInteger

class SaleViewModel(
    private val repository: SaleRepository
) : ViewModel() {

    val saleLines: MutableStateFlow<List<SaleLineUiModel>> = MutableStateFlow(emptyList())

    fun getStockByBranch(branchId: Int): Flow<List<StockUiModel>> = flow<List<StockUiModel>> {
        repository.getStockByBranchId(branchId).fold(
            onSuccess = { stockList ->
                emit(stockList.map { stock -> stock.toUiModel() })
            },
            onFailure = {
                emit(emptyList())
            }
        )
    }.flowOn(Dispatchers.IO)

    fun getClientByCuit(cuit: String, onFailureCallback: (Throwable) -> Unit): Flow<ClientUiModel> = flow {
        repository.getClientByCuit(cuit).fold(
            onSuccess = { client ->
                emit(client.toUiModel())
            },
            onFailure = onFailureCallback
        )
    }.flowOn(Dispatchers.IO)

    fun startSale(
        cardNumber: String,
        cardHolder: String,
        cardExpiryMonth: String,
        cardExpiryYear: String,
        cardCcv: String,
        clientCuit: String,
        onSuccessCallback: (String) -> Unit,
        onFailureCallback: (Throwable) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.startSale(
            sale = Sale(
                saleLines = saleLines.value.toDomainEntity(),
                card = Card(
                    number = cardNumber,
                    holder = cardHolder,
                    expiryMonth = cardExpiryMonth,
                    expiryYear = cardExpiryYear,
                    ccv = cardCcv
                ),
                amount = saleLines.value.getTotal().formatWithoutDecimalSeparator(),
                clientCuit = clientCuit
            ),
            type = PaymentType.CARD
        ).fold(
            onSuccess = onSuccessCallback,
            onFailure = onFailureCallback
        )
    }
    fun startSale(
        clientCuit: String,
        onSuccessCallback: (String) -> Unit,
        onFailureCallback: (Throwable) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.startSale(
            Sale(
                saleLines = saleLines.value.toDomainEntity(),
                card = null,
                amount = saleLines.value.getTotal().formatWithoutDecimalSeparator(),
                clientCuit = clientCuit
            ),
            type = PaymentType.CASH
        ).fold(
            onSuccess = onSuccessCallback,
            onFailure = onFailureCallback
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
    }

    val isNominalClient: Boolean
        get() = AnonymousClientRules.isNominalClient(saleLines.value.getTotal())


    fun removeSaleLineById(lineId: Int) {
        saleLines.value = saleLines.value.filterNot { line -> line.id == lineId }
    }

    fun cleanStates() {
        saleLines.value = emptyList()
    }
}