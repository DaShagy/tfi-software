package com.dshagapps.tfi_software.domain.repositories

import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.domain.entities.Sale
import com.dshagapps.tfi_software.domain.entities.Stock

interface SaleRepository {
    suspend fun getStockByBranchId(branchId: Int): Result<List<Stock>>
    suspend fun getClientByCuit(cuit: String): Result<Client>
    suspend fun startSale(sale: Sale): Result<String>
}