package com.dshagapps.tfi_software.presentation.utils

import java.text.DecimalFormat

fun Double.toPriceString(): String = "$${DecimalFormat("#.00").format(this)}"

fun Double.formatWithoutDecimalSeparator(): String {
    val formattedValue = String.format("%.2f", this)
    return formattedValue.replace(".", "")
}