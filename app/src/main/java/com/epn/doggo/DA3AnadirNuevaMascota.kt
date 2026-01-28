package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.String

class DA3AnadirNuevaMascota : AppCompatActivity() {

    private lateinit var duenoId: String
    private lateinit var petId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_5_1aniadir_nueva_mascota)
        applyInsets()

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
        // val nextId = intent.getIntExtra("EXTRA_NEXT_ID", -1)
        // OBETENCION DEL DUENO ID
        duenoId = intent.getStringExtra("usuario_id")
            ?: run {
                Toast.makeText(this, "Error: dueño no encontrado", Toast.LENGTH_LONG).show()
                finish()
                return
            }


        if (isEditing) {
            petId = intent.getStringExtra("pet_id")
                ?: run {
                    Toast.makeText(this, "Error: Mascota no encontrado", Toast.LENGTH_LONG).show()
                    finish()
                    return
                }
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
                if (rb.text.toString().equals(petToEdit.tamano, ignoreCase = true)) {
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
            if (tamanoSelec == -1) {
                Toast.makeText(this, "Debes elegir el tamaño de tu mascota!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val tamano = findViewById<RadioButton>(tamanoSelec).text.toString()

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
            // LLAMADO DE LA API
            val request = RegisterPetRequest(
                // id = if (isEditing) petToEdit!!.id else null,
                dueno_id = duenoId,
                nombre = nombre,
                raza = raza,
                edad = edadStr.toInt(),
                peso = pesoStr.toFloat(),
                tamano = tamano,
                comportamiento = comportamiento
            )
            ApiClient.api.registerPet(request)
                .enqueue(object : retrofit2.Callback<RegisterPetResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<RegisterPetResponse>,
                        response: retrofit2.Response<RegisterPetResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {

                            val petApi = response.body()!!.data

                            // Convertimos a tu modelo local (si lo sigues usando)
                            val mascota = Pet(
                                id = petApi.id,
                                dueno_id = petApi.dueno_id,
                                nombre = petApi.nombre,
                                raza = petApi.raza,
                                edad = petApi.edad,
                                tamano = petApi.tamano,
                                peso = petApi.peso,
                                comportamiento = petApi.comportamiento
                            )

                            val resultIntent = Intent()
                            if (isEditing) {
                                resultIntent.putExtra(DA2AnadirMascotas.EXTRA_UPDATED_PET, mascota)
                            } else {
                                resultIntent.putExtra(DA2AnadirMascotas.EXTRA_NEW_PET, mascota)
                            }

                            setResult(RESULT_OK, resultIntent)
                            finish()

                        } else {
                            Toast.makeText(
                                this@DA3AnadirNuevaMascota,
                                "Error al registrar mascota",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<RegisterPetResponse>, t: Throwable) {
                        Toast.makeText(
                            this@DA3AnadirNuevaMascota,
                            "No se pudo conectar al servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            // FINALIZA LLAMADO DE LA API
            /////////////////////////////////////////
//            val tamano = findViewById<RadioButton>(tamanoSelec).text.toString()
//            val idFinal = if (isEditing) petToEdit!!.id else nextId
//            if (idFinal == -1) {
//                Toast.makeText(this, "Error interno: no se generó ID", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            val mascota = Pet(
//                id = idFinal,
//                nombre = nombre,
//                raza = raza,
//                edad = edad!!,
//                tamanio = tamano,
//                peso = peso,
//                comportamiento = comportamiento
//            )
//            val resultIntent = Intent()
//            if (isEditing) {
//                resultIntent.putExtra(DA2AnadirMascotas.EXTRA_UPDATED_PET, mascota)
//            } else {
//                resultIntent.putExtra(DA2AnadirMascotas.EXTRA_NEW_PET, mascota)
//            }
//            setResult(RESULT_OK, resultIntent)
//            finish()
        }
    }

    private fun applyInsets() {
        val root = findViewById<View>(R.id.root)
        val topHeader = findViewById<View>(R.id.topHeader)
        val bottomSection = findViewById<View>(R.id.bottomSection)

        val topPad = topHeader.paddingTop
        val bottomPad = bottomSection.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Empuja el header hacia abajo (status bar)
            topHeader.setPadding(
                topHeader.paddingLeft,
                topPad + sysBars.top,
                topHeader.paddingRight,
                topHeader.paddingBottom
            )

            // Empuja el bottom hacia arriba (navigation bar)
            bottomSection.setPadding(
                bottomSection.paddingLeft,
                bottomSection.paddingTop,
                bottomSection.paddingRight,
                bottomPad + sysBars.bottom
            )

            insets
        }

        ViewCompat.requestApplyInsets(root)
    }
}
