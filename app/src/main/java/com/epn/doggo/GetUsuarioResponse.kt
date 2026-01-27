package com.epn.doggo

data class GetUsuarioResponse(
    val status: Int,
    val message: String,
    val data: UsuarioResponse
)

data class UsuarioResponse(
    val id: String,
    val email: String,
    val nombre_completo: String,
    val telefono: String,
    val direccion: String,
    val rol: String
)
