package com.dshagapps.tfi_software.domain.entities

import com.dshagapps.tfi_software.domain.enums.PaymentType
import com.dshagapps.tfi_software.domain.enums.TributeCondition
import java.util.Date
data class Receipt(
    val businessCuit: String,
    val businessAddress: String,
    val businessTributeCondition: TributeCondition,
    val salePoint: Int,
    val salesmanName: String,
    val saleNumber: Int,
    val paymentType: PaymentType,
    val receiptLines: List<ReceiptLine>,
    val customerCuit: String,
    val customerTributeCondition: TributeCondition,
    val receiptType: String,
    val caeNumber: String,
    val caeExpirationDate: Date,
    val saleTaxedAmount: String,
    val saleTaxAmount: String,
    val saleTotalAmount: String,
    val issueDate: Date
)

data class ReceiptLine(
    val id: Int,
    val code: Int,
    val quantity: Int,
    val lineTotal: String,
    val description: String,
    val unitPrice: String,
    val iva: String
)
