package com.epn.doggo.data

data class ChatMessage(
    val id: String? = null,
    val paseo_id: String,
    val emisor_id: String,
    val receptor_id: String,
    val contenido: String,
    val creado_en: String? = null,
    val leido: Boolean = false,
    val isMine: Boolean = false
)
