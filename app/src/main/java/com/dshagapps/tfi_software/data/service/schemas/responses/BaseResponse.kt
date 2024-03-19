package com.dshagapps.tfi_software.data.service.schemas.responses

data class BaseResponse<T> (
    val content: ArrayList<T>,
    val error: ArrayList<String>
)
