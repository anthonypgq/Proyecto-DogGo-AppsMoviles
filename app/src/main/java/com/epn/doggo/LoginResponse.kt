package com.epn.doggo

data class LoginResponse(
    val status: Int,
    val message: String,
    val data: UserApi
)

data class UserApi(
    val id: String,
    val email: String,
    val nombre_completo: String,
    val telefono: String,
    val direccion: String,
    val rol: String
)
