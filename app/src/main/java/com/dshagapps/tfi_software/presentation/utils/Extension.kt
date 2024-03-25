package com.dshagapps.tfi_software.presentation.utils

import java.text.DecimalFormat

fun Double.toPriceString(): String = DecimalFormat.getCurrencyInstance().format(this / 100)

fun Double.formatWithoutDecimalSeparator(): String {
    val formattedValue = String.format("%.2f", this)
    return formattedValue.replace(".", "")
}