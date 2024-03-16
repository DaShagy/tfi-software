package com.dshagapps.tfi_software.data.repositories

import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.data.toStock
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.repositories.SaleRepository

class SaleRepositoryImpl(
    private val api: Api
): SaleRepository {
    override suspend fun getStockByBranchId(branchId: Int): Result<List<Stock>> {
        api.getStock(branchId).execute().also {
            return if (it.isSuccessful) {
                Result.success(
                    it.body()!!.content.map { response -> response.toStock() }
                )
            } else {
                Result.failure(
                    Exception(it.body()?.error?.get(0) ?: "Unexpected error")
                )
            }
        }
    }
}