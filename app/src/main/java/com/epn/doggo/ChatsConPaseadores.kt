package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ChatsConPaseadores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_11chats_con_paseadores)

        // Items de chat (estáticos en tu XML)
        val item1 = findViewById<LinearLayout>(R.id.lin11ItemChat1)
        val item2 = findViewById<LinearLayout>(R.id.lin11ItemChat2)
        val item3 = findViewById<LinearLayout>(R.id.lin11ItemChat3)

        item1.setOnClickListener {
            abrirChatIndividual(
                nombre = getString(R.string.str11ChatAnaNombre),
                estado = getString(R.string.str12EstadoEnLinea)
            )
        }

        item2.setOnClickListener {
            abrirChatIndividual(
                nombre = getString(R.string.str11ChatCarlosNombre),
                estado = getString(R.string.str12EstadoEnLinea)
            )
        }

        item3.setOnClickListener {
            abrirChatIndividual(
                nombre = getString(R.string.str11ChatMariaNombre),
                estado = getString(R.string.str12EstadoEnLinea)
            )
        }

        // Bottom nav (si luego quieres navegación real)
        val navInicio = findViewById<LinearLayout>(R.id.lin11NavInicio)
        val navAgenda = findViewById<LinearLayout>(R.id.lin11NavAgenda)
        val navChat = findViewById<LinearLayout>(R.id.lin11NavChat)
        val navPerfil = findViewById<LinearLayout>(R.id.lin11NavPerfil)

        navInicio.setOnClickListener {
            startActivity(Intent(this, DashboardPaseador::class.java))
        }

        navAgenda.setOnClickListener {
            // startActivity(Intent(this, Agenda::class.java))
        }

        navChat.setOnClickListener {
            // Ya estás en Chat
        }

        navPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilPaseador::class.java))
        }
    }

    private fun abrirChatIndividual(nombre: String, estado: String) {
        val intent = Intent(this, ChatIndividual::class.java)
        intent.putExtra("ext11NombreChat", nombre)
        intent.putExtra("ext11EstadoChat", estado)
        startActivity(intent)
    }
}