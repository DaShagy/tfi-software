package com.dshagapps.tfi_software.data.service.api

import com.dshagapps.tfi_software.data.service.response.BaseResponse
import com.dshagapps.tfi_software.data.service.response.StockResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/api/stock/branch/{branchId}")
    fun getStock(@Path("branchId")branchId: Int): Call<BaseResponse<StockResponse>>
}