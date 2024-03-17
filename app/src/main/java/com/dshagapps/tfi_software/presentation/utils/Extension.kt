package com.dshagapps.tfi_software.presentation.utils

import java.text.DecimalFormat

fun Double.toPriceString(): String = "$${DecimalFormat("#.00").format(this)}"