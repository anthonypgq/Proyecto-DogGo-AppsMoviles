package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AniadirNuevaMascotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_5_1aniadir_nueva_mascota)

        val btnSavePet = findViewById<Button>(R.id.btnSavePet)

        btnSavePet.setOnClickListener {
            val intent = Intent(this, Activity_aniadir_mascotas::class.java)
            startActivity(intent)
        }
    }
}