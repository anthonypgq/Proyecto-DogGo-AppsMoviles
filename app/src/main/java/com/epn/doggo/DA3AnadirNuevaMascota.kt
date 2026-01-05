package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DA3AnadirNuevaMascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_5_1aniadir_nueva_mascota)

        // Referencias de UI
        val btnSavePet = findViewById<Button>(R.id.btnSavePet)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val rgTamano = findViewById<RadioGroup>(R.id.rgTamano)

        val layoutNombre = findViewById<TextInputLayout>(R.id.inputNamePet)
        val layoutRaza = findViewById<TextInputLayout>(R.id.inputRaza)
        val layoutEdad = findViewById<TextInputLayout>(R.id.inputEdad)

        // Botón de regreso (flecha)
        btnBack.setOnClickListener {
            finish()
        }

        btnSavePet.setOnClickListener {
            val nombre = findViewById<TextInputEditText>(R.id.txtNamePet).text.toString()
            val raza = findViewById<TextInputEditText>(R.id.txtRaza).text.toString()
            val edad = findViewById<TextInputEditText>(R.id.txtEdad).text.toString()
            val peso = findViewById<TextInputEditText>(R.id.txtPeso).text.toString()
            val tamanoSelec = rgTamano.checkedRadioButtonId

            var isCorrect = true
            layoutNombre.error = null
            layoutRaza.error = null
            layoutEdad.error = null

            if (nombre.isBlank()) {
                layoutNombre.error = "Campo obligatorio"
                isCorrect = false
            }
            if (raza.isBlank()) {
                layoutRaza.error = "Campo obligatorio"
                isCorrect = false
            }
            if (edad.isBlank()) {
                layoutEdad.error = "Campo obligatorio"
                isCorrect = false
            }
            if (tamanoSelec == -1) {
                Toast.makeText(this, "Debes elegir el tamaño de tu mascota!", Toast.LENGTH_SHORT).show()
                isCorrect = false
            }

            if (isCorrect) {
                val tamano = findViewById<RadioButton>(tamanoSelec).text.toString()
                val nuevaMascota = Pet(nombre, raza, edad.toInt(), tamano, peso.toFloat())

                // El resultado a la actividad anterior
                val resultIntent = Intent()
                resultIntent.putExtra("EXTRA_NEW_PET", nuevaMascota)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}