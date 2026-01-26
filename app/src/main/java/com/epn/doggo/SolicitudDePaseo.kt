package com.epn.doggo

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SolicitudDePaseo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solicitud_de_paseo)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnRechazar = findViewById<Button>(R.id.btnRechazar)
        val btnAceptar = findViewById<Button>(R.id.btnAceptar)

        btnBack.setOnClickListener {
            finish()
        }

        btnRechazar.setOnClickListener {
            Toast.makeText(this, "Solicitud rechazada", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnAceptar.setOnClickListener {
            Toast.makeText(this, "Solicitud aceptada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}