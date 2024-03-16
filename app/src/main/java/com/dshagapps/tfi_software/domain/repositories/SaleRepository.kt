package com.dshagapps.tfi_software.domain.repositories

import com.dshagapps.tfi_software.domain.entities.Stock

interface SaleRepository {

    suspend fun getStockByBranchId(branchId: Int): Result<List<Stock>>
}