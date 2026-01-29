package com.epn.doggo

data class UserApi(
    val id: String,
    val email: String,
    val nombre_completo: String,
    val telefono: String,
    val direccion: String,
    val rol: String
)
