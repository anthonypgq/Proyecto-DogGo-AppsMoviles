package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class A1Bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnComenzar);
        val btnSesion = findViewById<Button>(R.id.btnSesion);

        btnStart.setOnClickListener {
            val intent = Intent(this, C1SeleccionRol::class.java)
            startActivity(intent)
        }

        btnSesion.setOnClickListener {
            val intent = Intent(this, B1Login::class.java)
            startActivity(intent)
        }
    }
}