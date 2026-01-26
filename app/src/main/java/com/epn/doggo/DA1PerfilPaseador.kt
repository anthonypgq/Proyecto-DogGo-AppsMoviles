package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DA1PerfilPaseador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_da1_perfil_paseador)

        val btnSaveAndContinue = findViewById<Button>(R.id.btnSaveAndContinue)

        btnSaveAndContinue.setOnClickListener {
            // Navegar a la siguiente pantalla (Horarios del Paseador)
            val intent = Intent(this, DA2HorariosPaseador::class.java)
            startActivity(intent)
        }
    }
}