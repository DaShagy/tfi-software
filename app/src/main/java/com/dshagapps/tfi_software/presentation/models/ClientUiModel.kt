package com.dshagapps.tfi_software.presentation.models

import com.dshagapps.tfi_software.domain.enums.TributeCondition

data class ClientUiModel(
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val cuit: String = "99999999999",
    val tributeCondition: TributeCondition = TributeCondition.CONSUMIDOR_FINAL
)
