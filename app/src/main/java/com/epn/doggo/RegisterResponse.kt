package com.epn.doggo

data class RegisterResponse(
    val status: Int,
    val message: String,
    val data: String // UUID del usuario
)
