package com.dshagapps.tfi_software.presentation.utils

import com.dshagapps.tfi_software.domain.entities.SaleLine
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.presentation.models.SaleLineUiModel
import com.dshagapps.tfi_software.presentation.models.StockUiModel

fun Stock.toUiModel(): StockUiModel =
    StockUiModel(
        id = id,
        productDescription = productDescription,
        sizeDescription = sizeDescription,
        colorDescription = colorDescription,
        price = price,
        maxQuantity = quantity
    )
