package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardPaseador : AppCompatActivity() {

    private lateinit var txtSolicitudesPend: TextView
    private lateinit var txtPaseosSemana: TextView
    private lateinit var txtSaludo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_10dashboard_paseador)

        // Inicializar vistas
        txtSolicitudesPend = findViewById(R.id.txt10SolicitudesPendNum)
        txtPaseosSemana = findViewById(R.id.txt10PaseosSemanaNum)
        txtSaludo = findViewById(R.id.txt10Saludo)

        val btnVerSolicitud1 = findViewById<Button>(R.id.btn10Solicitud1Ver)
        val btnVerSolicitud2 = findViewById<Button>(R.id.btn10Solicitud2Ver)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Obtener ID del paseador desde el Intent
        val paseadorId = intent.getStringExtra("usuario_id")

        if (paseadorId != null) {
            cargarDatosUsuario(paseadorId)
            cargarEstadisticas(paseadorId)
        } else {
            Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
        }

        // Configurar selección actual en el menú
        bottomNavigationView.selectedItemId = R.id.nav_inicio

        // Navegación
        btnVerSolicitud1.setOnClickListener {
            startActivity(Intent(this, SolicitudDePaseo::class.java))
        }

        btnVerSolicitud2.setOnClickListener {
            startActivity(Intent(this, SolicitudDePaseo::class.java))
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> true
                R.id.nav_paseos -> {
                    startActivity(Intent(this, Paseos::class.java))
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

    private fun cargarDatosUsuario(id: String) {
        ApiClient.api.getUsuario(id).enqueue(object : Callback<GetUsuarioResponse> {
            override fun onResponse(call: Call<GetUsuarioResponse>, response: Response<GetUsuarioResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val nombre = response.body()!!.data.nombre_completo
                    txtSaludo.text = "¡Hola, $nombre!"
                }
            }

            override fun onFailure(call: Call<GetUsuarioResponse>, t: Throwable) {
                // No es crítico mostrar error aquí si falla el nombre
            }
        })
    }

    private fun cargarEstadisticas(id: String) {
        ApiClient.api.getEstadisticasPaseador(id).enqueue(object : Callback<EstadisticasPaseadorResponse> {
            override fun onResponse(
                call: Call<EstadisticasPaseadorResponse>,
                response: Response<EstadisticasPaseadorResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val stats = response.body()!!.data
                    txtSolicitudesPend.text = stats.solicitudes_pendientes.toString()
                    txtPaseosSemana.text = stats.solicitudes_semana_actual.toString()
                }
            }

            override fun onFailure(call: Call<EstadisticasPaseadorResponse>, t: Throwable) {
                Toast.makeText(this@DashboardPaseador, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
