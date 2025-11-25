package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Activity_aniadir_mascotas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_5aniadir_mascotas)

        val btnFinishAndNExt = findViewById<Button>(R.id.btnFinishAndNExt)
        val btnAddPet = findViewById<Button>(R.id.btnAddPet)

        btnFinishAndNExt.setOnClickListener {
            val intent = Intent(this, HomeDuenio::class.java)
            startActivity(intent)
        }

        btnAddPet.setOnClickListener {
            val intent = Intent(this, AniadirNuevaMascotaActivity::class.java)
            startActivity(intent)
        }
    }
}