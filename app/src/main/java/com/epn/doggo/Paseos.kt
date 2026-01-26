package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Paseos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_paseos)

        val rvSolicitudes = findViewById<RecyclerView>(R.id.rvSolicitudesEntrantes)
        val rvHistorial = findViewById<RecyclerView>(R.id.rvPaseosRealizados)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Marcar el item de Paseos como seleccionado
        bottomNavigationView.selectedItemId = R.id.nav_paseos

        // Datos de ejemplo para Solicitudes Entrantes
        val solicitudes = listOf(
            PaseoSolicitud("Max", "Hoy 3:00 PM • 1 hora", "$ 20"),
            PaseoSolicitud("Luna", "Mañana 10:00 AM • 1.5 horas", "$ 30")
        )

        // Datos de ejemplo para Historial
        val historial = listOf(
            PaseoSolicitud("Toby", "Ayer • 1 hora", "$ 18", true),
            PaseoSolicitud("Rocky", "12 Oct • 2 horas", "$ 35", true),
            PaseoSolicitud("Mora", "10 Oct • 1 hora", "$ 18", true)
        )

        // Configurar RecyclerView Solicitudes
        rvSolicitudes.layoutManager = LinearLayoutManager(this)
        rvSolicitudes.adapter = PaseoAdapter(solicitudes) { 
            // Acción al pulsar "Ver"
            val intent = Intent(this, SolicitudDePaseo::class.java)
            startActivity(intent)
        }

        // Configurar RecyclerView Historial
        rvHistorial.layoutManager = LinearLayoutManager(this)
        rvHistorial.adapter = PaseoAdapter(historial)

        // Configurar navegación
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    startActivity(Intent(this, DashboardPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_paseos -> true
                R.id.nav_chat -> {
                    startActivity(Intent(this, ChatSolicitudesPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, MiPerfilPaseador::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}