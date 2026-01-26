package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat


class C1SeleccionRol : AppCompatActivity() {

    private lateinit var cardOwner: CardView
    private lateinit var cardWalker: CardView
    private lateinit var btnNext: Button

    // 0 = nada, 1 = dueño, 2 = paseador
    private var rolSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_3rol_selection)

        // Referencias
        cardOwner = findViewById(R.id.cardOwner)
        cardWalker = findViewById(R.id.cardWalker)
        btnNext = findViewById(R.id.btnNext)

        // Selección de Dueño
        cardOwner.setOnClickListener {
            rolSeleccionado = 1
            marcarSeleccion(ownerSeleccionado = true)
        }

        // Selección de Paseador
        cardWalker.setOnClickListener {
            rolSeleccionado = 2
            marcarSeleccion(ownerSeleccionado = false)
        }

        // Continuar
        btnNext.setOnClickListener {
            when (rolSeleccionado) {
                1 -> {
                    // Ir a PerfilDuenio
                    startActivity(Intent(this, DA1PerfilDuenio::class.java))
                }
                2 -> {
                    // Ir a PerfilPaseador
                    startActivity(Intent(this, DA1PerfilPaseador::class.java))
                }
                else -> {
                    Toast.makeText(this, "Selecciona un rol para continuar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Solo UI para que se vea cuál está seleccionada.
     * Puedes ajustar colores a tu gusto.
     */
    private fun marcarSeleccion(ownerSeleccionado: Boolean) {
        if (ownerSeleccionado) {
            cardOwner.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.blue_text)
            )
            cardWalker.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.white)
            )
        } else {
            cardWalker.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.blue_text)
            )
            cardOwner.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.white)
            )
        }
    }

}
