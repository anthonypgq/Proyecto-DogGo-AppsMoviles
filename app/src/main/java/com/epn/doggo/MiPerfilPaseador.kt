package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MiPerfilPaseador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mi_perfil_paseador)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        
        // Referencias a los campos y botones de edición
        val txtNombreValue = findViewById<TextView>(R.id.txtNombreValue)
        val txtEmailValue = findViewById<TextView>(R.id.txtEmailValue)
        val txtBioValue = findViewById<TextView>(R.id.txtBioValue)
        val txtRateValue = findViewById<TextView>(R.id.txtRateValue)

        val editIconNombre = findViewById<ImageView>(R.id.editIconNombre)
        val editIconEmail = findViewById<ImageView>(R.id.editIconEmail)
        val editIconBio = findViewById<ImageView>(R.id.editIconBio)
        val editIconRate = findViewById<ImageView>(R.id.editIconRate)

        // Configurar selección actual en el menú
        bottomNavigationView.selectedItemId = R.id.nav_perfil

        // Lógica de edición individual
        editIconNombre.setOnClickListener { mostrarDialogoEdicion("Nombre", txtNombreValue) }
        editIconEmail.setOnClickListener { mostrarDialogoEdicion("Correo", txtEmailValue) }
        editIconBio.setOnClickListener { mostrarDialogoEdicion("Biografía", txtBioValue) }
        editIconRate.setOnClickListener { mostrarDialogoEdicion("Tarifa por hora", txtRateValue) }

        // Configurar navegación
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    startActivity(Intent(this, DashboardPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_paseos -> {
                    startActivity(Intent(this, Paseos::class.java))
                    finish()
                    true
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, ChatSolicitudesPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_perfil -> true
                else -> false
            }
        }
    }

    private fun mostrarDialogoEdicion(titulo: String, textView: TextView) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar $titulo")

        val input = EditText(this)
        input.setText(textView.text.toString())
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val nuevoValor = input.text.toString()
            if (nuevoValor.isNotBlank()) {
                textView.text = nuevoValor
                Toast.makeText(this, "$titulo actualizado", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}