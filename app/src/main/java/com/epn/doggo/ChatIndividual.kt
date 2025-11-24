package com.epn.doggo

import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ChatIndividual : AppCompatActivity() {

    private lateinit var txtNombreTop: TextView
    private lateinit var txtEstadoTop: TextView
    private lateinit var imgAtras: ImageView

    private lateinit var edtMensaje: EditText
    private lateinit var btnEnviar: CardView
    private lateinit var contenedorMensajes: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_12chat_individual)

        // Top bar
        txtNombreTop = findViewById(R.id.txt12NombreTop)
        txtEstadoTop = findViewById(R.id.txt12EstadoTop)
        imgAtras = findViewById(R.id.img12BtnAtras)

        // Input bar
        edtMensaje = findViewById(R.id.edt12EscribirMensaje)
        btnEnviar = findViewById(R.id.crd12BtnEnviar)

        // Contenedor mensajes
        contenedorMensajes = findViewById(R.id.lin12ContenedorMensajes)

        // Recibir datos desde ListaChats
        val nombre = intent.getStringExtra("ext11NombreChat")
            ?: getString(R.string.str12NombreDefault)

        val estado = intent.getStringExtra("ext11EstadoChat")
            ?: getString(R.string.str12EstadoEnLinea)

        txtNombreTop.text = nombre
        txtEstadoTop.text = estado

        imgAtras.setOnClickListener { finish() }

        btnEnviar.setOnClickListener {
            val texto = edtMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                agregarMensajeDerecha(texto)
                edtMensaje.setText("")
            }
        }
    }

    /**
     * Agrega una burbuja azul alineada a la derecha,
     * consistente con el estilo de tu XML.
     */
    private fun agregarMensajeDerecha(mensaje: String) {
        // Wrapper para mantener gravedad a la derecha
        val wrapper = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also { it.topMargin = dp(10) }
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.END
        }

        // Card azul
        val card = CardView(this).apply {
            radius = dp(14).toFloat()
            cardElevation = 0f
            setCardBackgroundColor(0xFF1E63FF.toInt())
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val inner = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), dp(10), dp(12), dp(10))
        }

        val txtMsg = TextView(this).apply {
            text = mensaje
            textSize = 15f
            setTextColor(0xFFFFFFFF.toInt())
        }

        val txtHora = TextView(this).apply {
            text = "Ahora"
            textSize = 12f
            setTextColor(0xFFDCE6FF.toInt())
            setPadding(0, dp(6), 0, 0)
        }

        inner.addView(txtMsg)
        inner.addView(txtHora)
        card.addView(inner)
        wrapper.addView(card)

        contenedorMensajes.addView(wrapper)
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
