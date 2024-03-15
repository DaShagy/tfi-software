package com.dshagapps.tfi_software.data.service.response

data class BaseResponse<T> (
    var content: ArrayList<T>,
    var error: ArrayList<String>
)
