package com.epn.doggo.data

import okhttp3.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class SocketManager(private val listener: MessageListener) {

    interface MessageListener {
        fun onMessageReceived(message: ChatMessage)
        fun onConnectionError(error: String)
    }

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    fun connect(userId: String) {
        val request = Request.Builder()
            .url("wss://doggo-api-99bf.onrender.com/ws/chat/$userId")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val json = JSONObject(text)
                    val msg = ChatMessage(
                        id = json.optString("id"),
                        paseo_id = json.getString("paseo_id"),
                        emisor_id = json.getString("emisor_id"),
                        receptor_id = json.getString("receptor_id"),
                        contenido = json.getString("contenido"),
                        creado_en = json.optString("creado_en"),
                        leido = json.optBoolean("leido", false),
                        isMine = false
                    )
                    listener.onMessageReceived(msg)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                listener.onConnectionError(t.message ?: "Error de conexión")
            }
        })
    }

    fun sendMessage(message: ChatMessage) {
        val json = JSONObject().apply {
            put("paseo_id", message.paseo_id)
            put("emisor_id", message.emisor_id)
            put("receptor_id", message.receptor_id)
            put("contenido", message.contenido)
        }
        webSocket?.send(json.toString())
    }

    fun disconnect() {
        webSocket?.close(1000, "Cierre normal")
    }
}
