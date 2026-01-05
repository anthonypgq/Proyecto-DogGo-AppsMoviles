package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardPaseador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_10dashboard_paseador)

        // Botones Aceptar / Rechazar solicitud 1
        val btnRechazar1 = findViewById<Button>(R.id.btn10Solicitud1Rechazar)
        val btnAceptar1 = findViewById<Button>(R.id.btn10Solicitud1Aceptar)

        // Botones Aceptar / Rechazar solicitud 2
        val btnRechazar2 = findViewById<Button>(R.id.btn10Solicitud2Rechazar)
        val btnAceptar2 = findViewById<Button>(R.id.btn10Solicitud2Aceptar)

        btnRechazar1.setOnClickListener {
            Toast.makeText(this, "Solicitud de Max rechazada", Toast.LENGTH_SHORT).show()
        }

        btnAceptar1.setOnClickListener {
            Toast.makeText(this, "Solicitud de Max aceptada", Toast.LENGTH_SHORT).show()
        }

        btnRechazar2.setOnClickListener {
            Toast.makeText(this, "Solicitud de Luna rechazada", Toast.LENGTH_SHORT).show()
        }

        btnAceptar2.setOnClickListener {
            Toast.makeText(this, "Solicitud de Luna aceptada", Toast.LENGTH_SHORT).show()
        }

        // Bottom Nav (navegación local simple)
        val navInicio = findViewById<LinearLayout>(R.id.lin10NavInicio)
        val navAgenda = findViewById<LinearLayout>(R.id.lin10NavAgenda)
        val navChat = findViewById<LinearLayout>(R.id.lin10NavChat)
        val navPerfil = findViewById<LinearLayout>(R.id.lin10NavPerfil)

        navInicio.setOnClickListener {
            // Ya estás en Inicio, no hace nada
        }

        navAgenda.setOnClickListener {
            Toast.makeText(this, "Agenda (pendiente)", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, Agenda::class.java))
        }

        navChat.setOnClickListener {
            startActivity(Intent(this, DB2ChatPaseador::class.java))
        }

        navPerfil.setOnClickListener {
            Toast.makeText(this, "Perfil (pendiente)", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, Perfil::class.java))
        }
    }
}
