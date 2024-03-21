package com.dshagapps.tfi_software.presentation.utils

import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.presentation.models.ClientUiModel
import com.dshagapps.tfi_software.presentation.models.StockUiModel

fun Stock.toUiModel(): StockUiModel =
    StockUiModel(
        id = id,
        productId = productId,
        productDescription = productDescription,
        sizeDescription = sizeDescription,
        colorDescription = colorDescription,
        price = price,
        maxQuantity = quantity,
        brandDescription = brandDescription,
        categoryDescription = categoryDescription
    )

fun Client.toUiModel(): ClientUiModel =
    ClientUiModel(
        firstName,
        lastName,
        address,
        cuit,
        tributeCondition
    )