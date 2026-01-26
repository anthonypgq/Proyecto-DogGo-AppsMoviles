package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardPaseador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_10dashboard_paseador)

        // Referencias a los botones "Ver" de las solicitudes recientes
        val btnVerSolicitud1 = findViewById<Button>(R.id.btn10Solicitud1Ver)
        val btnVerSolicitud2 = findViewById<Button>(R.id.btn10Solicitud2Ver)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Configurar selección actual en el menú
        bottomNavigationView.selectedItemId = R.id.nav_inicio

        // Navegación al detalle de la solicitud
        btnVerSolicitud1.setOnClickListener {
            val intent = Intent(this, SolicitudDePaseo::class.java)
            startActivity(intent)
        }

        btnVerSolicitud2.setOnClickListener {
            val intent = Intent(this, SolicitudDePaseo::class.java)
            startActivity(intent)
        }

        // Configurar navegación del menú inferior
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> true
                R.id.nav_paseos -> {
                    val intent = Intent(this, Paseos::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, ChatSolicitudesPaseador::class.java))
                    true
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, MiPerfilPaseador::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
