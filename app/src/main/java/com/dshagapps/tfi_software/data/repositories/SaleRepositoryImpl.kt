package com.dshagapps.tfi_software.data.repositories

import com.dshagapps.tfi_software.data.service.adapters.ErrorResponseDeserializer.deserialize
import com.dshagapps.tfi_software.data.service.api.Api
import com.dshagapps.tfi_software.data.utils.toClient
import com.dshagapps.tfi_software.data.utils.toRequestBody
import com.dshagapps.tfi_software.data.utils.toStock
import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.domain.entities.Sale
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.enums.PaymentType
import com.dshagapps.tfi_software.domain.repositories.SaleRepository

class SaleRepositoryImpl(
    private val api: Api
): SaleRepository {
    override suspend fun getStockByBranchId(branchId: Int): Result<List<Stock>> {
        api.getStockByBranch(branchId).execute().also { response ->
            return if (response.isSuccessful) {
                Result.success(
                    response.body()!!.content.map { stockResponse -> stockResponse.toStock() }
                )
            } else {
                Result.failure(
                    Exception(deserialize(response.errorBody()).message)
                )
            }
        }
    }

    override suspend fun getClientByCuit(cuit: String): Result<Client> {
        api.getClientByCuit(cuit).execute().also { response ->
            return if (response.isSuccessful) {
                Result.success(response.body()!!.content.toClient())
            } else {
                Result.failure(
                    Exception(deserialize(response.errorBody()).message)
                )
            }
        }
    }

    override suspend fun startSale(sale: Sale, type: PaymentType): Result<String> {
        val paymentType = when (type) {
            PaymentType.CASH -> "EFECTIVO"
            PaymentType.CARD -> "TARJETA"
        }
        api.startSale(sale.toRequestBody(), paymentType).execute().also { response ->
            return if (response.isSuccessful) {
                Result.success(response.body()!!.content)
            } else {
                Result.failure(
                    Exception(deserialize(response.errorBody()).message)
                )
            }
        }
    }
}