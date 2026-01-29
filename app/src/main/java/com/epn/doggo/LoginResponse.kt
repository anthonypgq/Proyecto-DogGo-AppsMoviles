package com.epn.doggo

data class LoginResponse(
    val status: Int,
    val message: String,
    val data: UserApi
)
