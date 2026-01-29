package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DA1PerfilPaseador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_da1_perfil_paseador)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val etBio = findViewById<EditText>(R.id.etBio)
        val etRate = findViewById<EditText>(R.id.etRate)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)
        val btnSaveAndContinue = findViewById<Button>(R.id.btnSaveAndContinue)

        btnSaveAndContinue.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val bio = etBio.text.toString().trim()
            val rate = etRate.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || 
                phone.isEmpty() || address.isEmpty() || rate.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Pasar todos los datos a la siguiente pantalla
            val intent = Intent(this, DA2HorariosPaseador::class.java).apply {
                putExtra("name", name)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("phone", phone)
                putExtra("address", address)
                putExtra("bio", bio)
                putExtra("rate", rate.toIntOrNull() ?: 0)
            }
            startActivity(intent)
        }
    }
}
