package com.dshagapps.tfi_software.data

import com.dshagapps.tfi_software.data.service.response.StockResponse
import com.dshagapps.tfi_software.domain.entities.Stock

fun StockResponse.toStock(): Stock =
    Stock(
        id = id,
        productId = articuloId,
        sizeId = talleId,
        colorId = colorId,
        productDescription = articulo,
        sizeDescription = talle,
        colorDescription = color,
        price = precio.toDoubleOrNull() ?: 0.0,
        quantity = cantidad
    )