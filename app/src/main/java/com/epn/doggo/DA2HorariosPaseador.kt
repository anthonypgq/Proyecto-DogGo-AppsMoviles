package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DA2HorariosPaseador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_da2_horarios_paseador)

        val btnFinish = findViewById<Button>(R.id.btnFinish)

        btnFinish.setOnClickListener {
            Toast.makeText(this, "¡Configuración completada!", Toast.LENGTH_SHORT).show()
            
            // Navegar al Dashboard del Paseador
            val intent = Intent(this, DashboardPaseador::class.java)
            startActivity(intent)
            finish()
        }
    }
}