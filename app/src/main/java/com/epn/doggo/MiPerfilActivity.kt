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

class MiPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_6mi_perfil)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        
        // Referencias a los campos y botones de edición
        val valueName = findViewById<TextView>(R.id.valueName)
        val valueEmail = findViewById<TextView>(R.id.valueEmail)
        val valuePhone = findViewById<TextView>(R.id.valuePhone)
        val valueAddress = findViewById<TextView>(R.id.valueAddress)

        val editName = findViewById<ImageView>(R.id.editName)
        val editEmail = findViewById<ImageView>(R.id.editEmail)
        val editPhone = findViewById<ImageView>(R.id.editPhone)
        val editAddress = findViewById<ImageView>(R.id.editAddress)

        // Configurar selección actual en el menú
        bottomNavigationView.selectedItemId = R.id.nav_perfil

        // Lógica de edición
        editName.setOnClickListener { mostrarDialogoEdicion("Nombre", valueName) }
        editEmail.setOnClickListener { mostrarDialogoEdicion("Correo", valueEmail) }
        editPhone.setOnClickListener { mostrarDialogoEdicion("Teléfono", valuePhone) }
        editAddress.setOnClickListener { mostrarDialogoEdicion("Dirección", valueAddress) }

        // Configurar navegación
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    startActivity(Intent(this, HomeDuenio::class.java))
                    finish()
                    true
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, DB2ChatPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_mascotas -> {
                    startActivity(Intent(this, MascotasActivity::class.java))
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