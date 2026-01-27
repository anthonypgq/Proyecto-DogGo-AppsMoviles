package com.epn.doggo

data class RegisterPetRequest(
    val dueno_id: String,
    val nombre: String,
    val raza: String,
    val edad: Int,
    val peso: Float,
    val tamano: String,
    val comportamiento: String
)
