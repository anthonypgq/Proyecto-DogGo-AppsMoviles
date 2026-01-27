package com.epn.doggo

data class GetPaseadoresResponse(
    val status: Int,
    val message: String,
    val data: List<PaseadorResponse>
)

data class PaseadorResponse(
    val id: String,
    val email: String,
    val nombre_completo: String,
    val telefono: String,
    val direccion: String,
    val rol: String,
    val paseadores: PaseadorInfo
)
data class PaseadorInfo(
    val biografia: String,
    val tarifa_hora: Double,
    val zona_servicio: String
)
