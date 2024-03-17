package com.dshagapps.tfi_software.data.utils

import com.dshagapps.tfi_software.data.service.response.ClientResponse
import com.dshagapps.tfi_software.data.service.response.StockResponse
import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.enums.TributeCondition

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

fun ClientResponse.toClient(): Client =
    Client(
        firstName = nombre,
        lastName = apellido,
        address = domicilio,
        cuit = CUIT,
        tributeCondition = TributeCondition.fromValue(condicionTributariaId)
    )