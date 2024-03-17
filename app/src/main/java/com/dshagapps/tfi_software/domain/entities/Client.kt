package com.dshagapps.tfi_software.domain.entities

import com.dshagapps.tfi_software.domain.enums.TributeCondition

data class Client(
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val cuit: String = "99999999999",
    val tributeCondition: TributeCondition = TributeCondition.CONSUMIDOR_FINAL
)
