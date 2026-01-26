package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatSolicitudesPaseador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_solicitudes_paseador)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        
        // Referencias a los items de chat
        val chatItem1 = findViewById<View>(R.id.chatItem1)
        val chatItem2 = findViewById<View>(R.id.chatItem2)
        val chatItem3 = findViewById<View>(R.id.chatItem3)

        // Configurar selección actual
        bottomNavigationView.selectedItemId = R.id.nav_chat

        // Personalizar items con datos de dueños/mascotas
        configurarItemChat(chatItem1, "Juan Perez (Dueño de Toby)", "Ayer", "Toby se portó muy bien")
        configurarItemChat(chatItem2, "María López (Dueño de Rocky)", "12 Oct", "¿A qué hora pasas hoy?")
        configurarItemChat(chatItem3, "Roberto Díaz (Dueño de Mora)", "10 Oct", "Gracias por el paseo")

        // Navegación
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    startActivity(Intent(this, DashboardPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_paseos -> {
                    startActivity(Intent(this, Paseos::class.java))
                    finish()
                    true
                }
                R.id.nav_chat -> true
                R.id.nav_perfil -> {
                    startActivity(Intent(this, MiPerfilPaseador::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun configurarItemChat(view: View, nombre: String, fecha: String, ultimoMsg: String) {
        // Reutilizamos IDs del layout list_item_paseador para mantener consistencia
        view.findViewById<TextView>(R.id.walker_name).text = nombre
        view.findViewById<TextView>(R.id.walker_rating).text = fecha
        view.findViewById<TextView>(R.id.walker_walks).text = ultimoMsg
        
        // Ocultar elementos no necesarios para la vista de chat si existen
        view.findViewById<View>(R.id.walker_price)?.visibility = View.GONE
        view.findViewById<View>(R.id.walker_distance)?.visibility = View.GONE

        view.setOnClickListener {
            val intent = Intent(this, ChatIndividual::class.java)
            intent.putExtra("ext11NombreChat", nombre)
            intent.putExtra("ext11EstadoChat", "En línea")
            startActivity(intent)
        }
    }
}