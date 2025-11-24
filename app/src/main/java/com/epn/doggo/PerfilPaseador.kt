package com.epn.doggo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class PerfilPaseador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_8perfil_paseador)

        val backButton = findViewById<ImageView>(R.id.btn8Back)
        val solicitarButton = findViewById<Button>(R.id.btn8SolicitarPaseo)

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        solicitarButton.setOnClickListener {
            val intent = Intent(this, SolicitudPaseador::class.java)
            startActivity(intent)
        }
    }
}