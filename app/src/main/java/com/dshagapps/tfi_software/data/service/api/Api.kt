package com.dshagapps.tfi_software.data.service.api

import com.dshagapps.tfi_software.data.service.response.BaseResponse
import com.dshagapps.tfi_software.data.service.response.ClientResponse
import com.dshagapps.tfi_software.data.service.response.StockResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/api/stock/branch/{branchId}")
    fun getStockByBranch(@Path("branchId")branchId: Int): Call<BaseResponse<StockResponse>>

    @GET("/api/cliente")
    fun getClientByCuit(@Query("cuit") cuit: String): Call<BaseResponse<ClientResponse>>
}