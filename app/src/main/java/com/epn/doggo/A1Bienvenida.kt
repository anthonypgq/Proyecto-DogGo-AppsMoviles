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
    private var dbHelper: DbHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            dbHelper = DbHelper(this)
            val sessionUser = dbHelper?.getUser()
            
            if (sessionUser != null) {
                val intent = if (sessionUser.rol.equals("dueño", ignoreCase = true)) {
                    Intent(this, DB1HomeDuenio::class.java)
                } else {
                    Intent(this, DashboardPaseador::class.java)
                }
                intent.putExtra("usuario_id", sessionUser.id)
                startActivity(intent)
                finish()
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Si hay error en la DB, continuamos a la bienvenida normal
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        applyInsets()

        val btnStart = findViewById<Button>(R.id.btnComenzar)
        val btnSesion = findViewById<Button>(R.id.btnSesion)

        btnStart?.setOnClickListener {
            val intent = Intent(this, C1SeleccionRol::class.java)
            startActivity(intent)
        }

        btnSesion?.setOnClickListener {
            val intent = Intent(this, B1Login::class.java)
            startActivity(intent)
        }
    }

    private fun applyInsets() {
        val root = findViewById<View>(R.id.main)
        if (root == null) return

        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom)
            insets
        }
    }
}
