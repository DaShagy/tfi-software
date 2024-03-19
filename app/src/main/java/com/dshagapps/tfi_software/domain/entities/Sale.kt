package com.dshagapps.tfi_software.domain.entities

import java.math.BigInteger

data class Sale(
    val saleLines: List<SaleLine>,
    val card: Card,
    val amount: BigInteger
)

data class Card(
    val number: String,
    val holder: String,
    val expiry: String,
    val ccv: String
)

data class SaleLine(
    val stockId: Int,
    val quantity: Int
)