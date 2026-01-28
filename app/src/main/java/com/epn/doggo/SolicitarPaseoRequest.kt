package com.epn.doggo

data class SolicitarPaseoRequest(
    val paseador_id: String,
    val dueno_id: String,
    val mascota_id: String,
    val fecha: String,
    val hora_inicio: String,
    val duracion_minutos: Int,
    val estado: String,
    val notas: String,
    val precio_total: Double
)
