package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DA1PerfilDuenio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_4perfil_duenio)

        val btnNext = findViewById<Button>(R.id.btnNext)

        val layoutName = findViewById<TextInputLayout>(R.id.inputName)
        val layoutDirection = findViewById<TextInputLayout>(R.id.inputDirection)
        val layoutPhone = findViewById<TextInputLayout>(R.id.inputPhone)
        val checkTerms = findViewById<CheckBox>(R.id.checkTerms)

        btnNext.setOnClickListener {
            val fullName = findViewById<TextInputEditText>(R.id.txtName).text.toString()
            val direction = findViewById<TextInputEditText>(R.id.txtDirection).text.toString()
            val phone = findViewById<TextInputEditText>(R.id.txtPhone).text.toString()

            var isCorrect = true
            layoutName.error = null
            layoutDirection.error = null
            layoutPhone.error = null

            if (fullName.isBlank()) {
                layoutName.error = "Campo obligatorio"
                isCorrect = false
            }
            if (direction.isBlank()) {
                layoutDirection.error = "Campo obligatorio"
                isCorrect = false
            }
            if (phone.isBlank()) {
                layoutPhone.error = "Campo obligatorio"
                isCorrect = false
            }

            val termsAccepted = checkTerms.isChecked

            if (!termsAccepted) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                isCorrect = false
            }

            if (isCorrect) {
                val intent = Intent(this, DA2AnadirMascotas::class.java)
                startActivity(intent)

                // Aplicar la animación personalizada
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(
                        OVERRIDE_TRANSITION_OPEN,
                        R.anim.slide_up,
                        R.anim.stay
                    )
                } else {
                    @Suppress("DEPRECATION")
                    overridePendingTransition(R.anim.slide_up, R.anim.stay)
                }
            }
        }
    }
}