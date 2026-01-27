package com.epn.doggo

data class RegisterRequest(
    val email: String,
    val contrasena: String,
    val nombre_completo: String,
    val telefono: String,
    val direccion: String,
    val rol: String,
    val biografia: String,
    val tarifa_hora: Int,
    val zona_servicio: String
)
