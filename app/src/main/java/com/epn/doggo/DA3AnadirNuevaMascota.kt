package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
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

        // UI
        val btnSavePet = findViewById<Button>(R.id.btnSavePet)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val rgTamano = findViewById<RadioGroup>(R.id.rgTamano)
        val txtTitulo = findViewById<TextView>(R.id.txtTitulo)

        val layoutNombre = findViewById<TextInputLayout>(R.id.inputNamePet)
        val layoutRaza = findViewById<TextInputLayout>(R.id.inputRaza)
        val layoutEdad = findViewById<TextInputLayout>(R.id.inputEdad)

        val txtName = findViewById<TextInputEditText>(R.id.txtNamePet)
        val txtRaza = findViewById<TextInputEditText>(R.id.txtRaza)
        val txtEdad = findViewById<TextInputEditText>(R.id.txtEdad)
        val txtPeso = findViewById<TextInputEditText>(R.id.txtPeso)
        val txtComportamiento = findViewById<TextInputEditText>(R.id.txtComportamiento)

        // ¿viene para editar?
        val petToEdit = intent.getParcelableExtra<Pet>(DA2AnadirMascotas.EXTRA_PET_TO_EDIT)
        val isEditing = petToEdit != null

        // ID para nuevos
        val nextId = intent.getIntExtra("EXTRA_NEXT_ID", -1)

        if (isEditing) {
            txtTitulo.text = "Editar Mascota"
            btnSavePet.text = "Guardar cambios"

            // Precargar
            txtName.setText(petToEdit!!.nombre)
            txtRaza.setText(petToEdit.raza)
            txtEdad.setText(petToEdit.edad.toString())
            txtPeso.setText(petToEdit.peso.toString())
            txtComportamiento.setText(petToEdit.comportamiento)

            // Marcar tamaño
            for (i in 0 until rgTamano.childCount) {
                val rb = rgTamano.getChildAt(i) as? RadioButton ?: continue
                if (rb.text.toString().equals(petToEdit.tamanio, ignoreCase = true)) {
                    rgTamano.check(rb.id)
                    break
                }
            }
        } else {
            txtTitulo.text = getString(R.string.lbl51Title) // "Añadir Nueva Mascota" (según strings)
            btnSavePet.text = getString(R.string.btn51SavePet)
        }

        btnBack.setOnClickListener { finish() }

        btnSavePet.setOnClickListener {

            val nombre = txtName.text?.toString().orEmpty().trim()
            val raza = txtRaza.text?.toString().orEmpty().trim()
            val edadStr = txtEdad.text?.toString().orEmpty().trim()
            val pesoStr = txtPeso.text?.toString().orEmpty().trim()
            val comportamiento = txtComportamiento.text?.toString().orEmpty().trim()
            val tamanoSelec = rgTamano.checkedRadioButtonId

            var isCorrect = true
            layoutNombre.error = null
            layoutRaza.error = null
            layoutEdad.error = null

            if (nombre.isBlank()) { layoutNombre.error = "Campo obligatorio"; isCorrect = false }
            if (raza.isBlank()) { layoutRaza.error = "Campo obligatorio"; isCorrect = false }
            if (edadStr.isBlank()) { layoutEdad.error = "Campo obligatorio"; isCorrect = false }
            if (tamanoSelec == -1) {
                Toast.makeText(this, "Debes elegir el tamaño de tu mascota!", Toast.LENGTH_SHORT).show()
                isCorrect = false
            }

            val edad = edadStr.toIntOrNull()
            if (edad == null) {
                layoutEdad.error = "Edad inválida"
                isCorrect = false
            }

            val peso = pesoStr.toFloatOrNull() ?: 0f // si no ponen peso, queda 0
            if (!isCorrect) return@setOnClickListener

            val tamano = findViewById<RadioButton>(tamanoSelec).text.toString()

            val idFinal = if (isEditing) petToEdit!!.id else nextId
            if (idFinal == -1) {
                Toast.makeText(this, "Error interno: no se generó ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mascota = Pet(
                id = idFinal,
                nombre = nombre,
                raza = raza,
                edad = edad!!,
                tamanio = tamano,
                peso = peso,
                comportamiento = comportamiento
            )

            val resultIntent = Intent()
            if (isEditing) {
                resultIntent.putExtra(DA2AnadirMascotas.EXTRA_UPDATED_PET, mascota)
            } else {
                resultIntent.putExtra(DA2AnadirMascotas.EXTRA_NEW_PET, mascota)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
