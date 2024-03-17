package com.dshagapps.tfi_software.domain.entities

data class SaleLine(
    val id: Int,
    val stock: Stock,
    val quantity: Int
) {
    val subtotal: Double get() = stock.price * quantity
}
