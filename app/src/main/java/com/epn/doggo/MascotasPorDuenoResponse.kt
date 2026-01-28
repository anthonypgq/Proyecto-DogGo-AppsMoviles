package com.epn.doggo

data class MascotasPorDuenoResponse(
    val status: Int,
    val message: String,
    val data: List<MascotaApi>
)

data class MascotaApi(
    val id: String,
    val dueno_id: String,
    val nombre: String,
    val raza: String,
    val edad: Int,
    val peso: Float,
    val tamano: String,
    val comportamiento: String
)
