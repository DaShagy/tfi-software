package com.dshagapps.tfi_software.data.service.response

data class StockResponse(
    var id: Int,
    var cantidad: Int,
    var color: String,
    var talle: String,
    var articulo: String,
    var colorId: Int,
    var articuloId: Int,
    var talleId: Int,
    var precio: String
)
