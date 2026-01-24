package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DA1PerfilDuenio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_4perfil_duenio)

        applyInsets() // ✅ arregla título y botón

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
