package com.epn.doggo

data class EstadisticasPaseadorResponse(
    val status: Int,
    val message: String,
    val data: EstadisticasData
)

data class EstadisticasData(
    val solicitudes_pendientes: Int,
    val solicitudes_semana_actual: Int
)
