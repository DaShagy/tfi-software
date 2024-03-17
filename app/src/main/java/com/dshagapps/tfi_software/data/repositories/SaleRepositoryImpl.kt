package com.dshagapps.tfi_software.data.repositories

import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.data.utils.toClient
import com.dshagapps.tfi_software.data.utils.toStock
import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.repositories.SaleRepository

class SaleRepositoryImpl(
    private val api: Api
): SaleRepository {
    override suspend fun getStockByBranchId(branchId: Int): Result<List<Stock>> {
        api.getStockByBranch(branchId).execute().also {
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

    override suspend fun getClientByCuit(cuit: String): Result<Client> {
        api.getClientByCuit(cuit).execute().also { response ->
            return if (response.isSuccessful) {
                if (response.body()!!.content.isEmpty()) {
                    Result.failure(
                        Exception("Client not found")
                    )
                } else {
                    Result.success(
                        response.body()!!.content[0].toClient()
                    )
                }
            } else {
                Result.failure(
                    Exception(response.body()?.error?.get(0) ?: "Client not found")
                )
            }
        }
    }
}