package com.dshagapps.tfi_software.data.utils

import com.dshagapps.tfi_software.data.service.schemas.requests.CardRequestBody
import com.dshagapps.tfi_software.data.service.schemas.requests.SaleLineRequestBody
import com.dshagapps.tfi_software.data.service.schemas.requests.SaleRequestBody
import com.dshagapps.tfi_software.data.service.schemas.responses.ClientResponse
import com.dshagapps.tfi_software.data.service.schemas.responses.StockResponse
import com.dshagapps.tfi_software.domain.entities.Card
import com.dshagapps.tfi_software.domain.entities.Client
import com.dshagapps.tfi_software.domain.entities.Sale
import com.dshagapps.tfi_software.domain.entities.SaleLine
import com.dshagapps.tfi_software.domain.entities.Stock
import com.dshagapps.tfi_software.domain.enums.TributeCondition

internal fun StockResponse.toStock(): Stock =
    Stock(
        id = id,
        productId = articuloId,
        sizeId = talleId,
        colorId = colorId,
        productDescription = articulo,
        sizeDescription = talle,
        colorDescription = color,
        price = precio.toDoubleOrNull() ?: 0.0,
        quantity = cantidad,
        brandDescription = marca,
        categoryDescription = categoria
    )

internal fun ClientResponse.toClient(): Client =
    Client(
        firstName = nombre,
        lastName = apellido,
        address = domicilio,
        cuit = CUIT,
        tributeCondition = TributeCondition.fromValue(condicionTributariaId)
    )

internal fun Sale.toRequestBody(): SaleRequestBody =
    SaleRequestBody(
        lineasDeVenta = saleLines.toRequestBody(),
        tarjeta = card?.toRequestBody(),
        monto = amount,
        clienteCuit = clientCuit
    )

private fun Card.toRequestBody(): CardRequestBody =
    CardRequestBody(
        titular = holder,
        numero = number,
        mesVencimiento = expiryMonth,
        anioVencimiento = expiryYear,
        ccv = ccv
    )

private fun List<SaleLine>.toRequestBody(): List<SaleLineRequestBody> =
    this.map { line -> SaleLineRequestBody(stockId = line.stockId, cantidad = line.quantity) }
