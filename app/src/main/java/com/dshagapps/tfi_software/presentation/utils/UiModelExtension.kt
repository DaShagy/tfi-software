package com.dshagapps.tfi_software.presentation.utils

import com.dshagapps.tfi_software.presentation.models.SaleLineUiModel

fun SaleLineUiModel.incrementStockQuantity(): SaleLineUiModel =
    this.copy(stock = this.stock.copy(quantity = minOf(this.stock.quantity + 1, this.stock.maxQuantity)))

fun SaleLineUiModel.decrementStockQuantity(): SaleLineUiModel =
    this.copy(stock = this.stock.copy(quantity = maxOf(this.stock.quantity - 1, 0)))

fun List<SaleLineUiModel>.getTotal(): Double =
    this.sumOf { line -> line.subtotal }