package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.epn.doggo.data.DbHelper


class A1Bienvenida : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Verificar si hay una sesión activa antes de mostrar la bienvenida
        dbHelper = DbHelper(this)
        val sessionUser = dbHelper.getUser()
        if (sessionUser != null) {
            val intent = if (sessionUser.rol == "dueno") {
                Intent(this, DB1HomeDuenio::class.java)
            } else {
                Intent(this, DashboardPaseador::class.java)
            }
            intent.putExtra("usuario_id", sessionUser.id)
            startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        applyInsets()

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

    private fun applyInsets() {
        val root = findViewById<View>(R.id.main)

        val originalTop = root.paddingTop
        val originalBottom = root.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                v.paddingLeft,
                originalTop + sysBars.top,
                v.paddingRight,
                originalBottom + sysBars.bottom
            )
            insets
        }

        ViewCompat.requestApplyInsets(root)
    }
}