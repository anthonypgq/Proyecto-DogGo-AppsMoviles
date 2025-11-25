package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PerfilDuenio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_4perfil_duenio)

        val btnNext = findViewById<Button>(R.id.btnNext)

        btnNext.setOnClickListener {
            val intent = Intent(this, Activity_aniadir_mascotas::class.java)
            startActivity(intent)
        }
    }
}