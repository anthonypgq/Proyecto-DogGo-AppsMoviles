package com.epn.doggo

data class RegisterPetResponse(
    val status: Int,
    val message: String,
    val data: PetApi
)

data class PetApi(
    val id: String,
    val dueno_id: String,
    val nombre: String,
    val raza: String,
    val edad: Int,
    val peso: Float,
    val tamano: String,
    val comportamiento: String
)
