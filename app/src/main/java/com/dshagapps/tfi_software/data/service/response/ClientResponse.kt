package com.dshagapps.tfi_software.data.service.response

import java.math.BigInteger

data class ClientResponse(
    var id: Int,
    var nombre: String,
    var apellido: String,
    var domicilio: String,
    var CUIT: String,
    var condicionTributariaId: Int,
    var condicionTributaria: String
)
