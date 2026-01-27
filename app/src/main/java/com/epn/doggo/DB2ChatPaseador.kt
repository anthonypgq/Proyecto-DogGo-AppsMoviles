package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DB2ChatPaseador : AppCompatActivity() {

    private lateinit var duenoId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_11chats_con_paseadores)

        duenoId = intent.getStringExtra("usuario_id")
            ?: run {
                Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_LONG).show()
                finish()
                return
            }

        // Referencias a los items incluidos (usando los IDs definidos en el include)
        val item1 = findViewById<View>(R.id.lin11ItemChat1)
        val item2 = findViewById<View>(R.id.lin11ItemChat2)
        val item3 = findViewById<View>(R.id.lin11ItemChat3)
        
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Configurar selección actual en el menú (Ilumina el icono de chat)
        bottomNavigationView.selectedItemId = R.id.nav_chat

        // Personalizar datos de los items incluidos
        configurarItemChat(item1, "Ana Pérez", "4.7", "43 paseos") {
            abrirChatIndividual("Ana Pérez", getString(R.string.str12EstadoEnLinea))
        }

        configurarItemChat(item2, "Carlos Rodríguez", "4.8", "15 paseos") {
            abrirChatIndividual("Carlos Rodríguez", getString(R.string.str12EstadoEnLinea))
        }

        configurarItemChat(item3, "Maria González", "4.9", "127 paseos") {
            abrirChatIndividual("Maria González", getString(R.string.str12EstadoEnLinea))
        }

        // Configurar navegación
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    val intent = Intent(this, DB1HomeDuenio::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_chat -> true
                R.id.nav_mascotas -> {
                    val intent = Intent(this, MascotasActivity::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_perfil -> {
                    val intent = Intent(this, MiPerfilActivity::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun configurarItemChat(view: View, nombre: String, rating: String, paseos: String, onClick: () -> Unit) {
        view.findViewById<TextView>(R.id.walker_name).text = nombre
        view.findViewById<TextView>(R.id.walker_rating).text = rating
        view.findViewById<TextView>(R.id.walker_walks).text = paseos
        // Ocultamos elementos que no son necesarios en la vista de chat si se desea
        // view.findViewById<TextView>(R.id.walker_price).visibility = View.GONE
        
        view.setOnClickListener { onClick() }
    }

    private fun abrirChatIndividual(nombre: String, estado: String) {
        val intent = Intent(this, ChatIndividual::class.java)
        intent.putExtra("ext11NombreChat", nombre)
        intent.putExtra("ext11EstadoChat", estado)
        startActivity(intent)
    }
}