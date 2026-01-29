package com.epn.doggo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.epn.doggo.data.ChatMessage
import com.epn.doggo.data.DbHelper
import com.epn.doggo.data.SocketManager
import java.text.SimpleDateFormat
import java.util.*

class ChatIndividual : AppCompatActivity(), SocketManager.MessageListener {

    private lateinit var txtNombreTop: TextView
    private lateinit var txtEstadoTop: TextView
    private lateinit var imgAtras: ImageView

    private lateinit var edtMensaje: EditText
    private lateinit var btnEnviar: View
    private lateinit var contenedorMensajes: LinearLayout
    
    private lateinit var dbHelper: DbHelper
    private lateinit var socketManager: SocketManager
    
    private var myId: String = ""
    private var otherId: String = ""
    private var paseoId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_12chat_individual)

        dbHelper = DbHelper(this)
        myId = dbHelper.getUser()?.id ?: ""
        
        // Datos del intent
        otherId = intent.getStringExtra("usuario_id") ?: ""
        paseoId = intent.getStringExtra("paseo_id") ?: "default_id" // Necesario para la nueva estructura

        // Top bar
        txtNombreTop = findViewById(R.id.txt12NombreTop)
        txtEstadoTop = findViewById(R.id.txt12EstadoTop)
        imgAtras = findViewById(R.id.img12BtnAtras)

        // Input bar
        edtMensaje = findViewById(R.id.edt12EscribirMensaje)
        btnEnviar = findViewById(R.id.btn12Enviar)

        // Contenedor mensajes
        contenedorMensajes = findViewById(R.id.lin12ContenedorMensajes)

        txtNombreTop.text = intent.getStringExtra("ext11NombreChat") ?: "Chat"
        txtEstadoTop.text = intent.getStringExtra("ext11EstadoChat") ?: "En línea"

        imgAtras.setOnClickListener { finish() }

        // Inicializar Socket
        socketManager = SocketManager(this)
        if (myId.isNotEmpty()) {
            socketManager.connect(myId)
        }

        btnEnviar.setOnClickListener {
            val texto = edtMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                val message = ChatMessage(
                    paseo_id = paseoId,
                    emisor_id = myId,
                    receptor_id = otherId,
                    contenido = texto,
                    isMine = true
                )
                socketManager.sendMessage(message)
                agregarMensajeALaUI(message)
                edtMensaje.setText("")
            }
        }
    }

    override fun onMessageReceived(message: ChatMessage) {
        runOnUiThread {
            // Verificamos que el mensaje sea para esta conversación específica
            if (message.emisor_id == otherId && message.paseo_id == paseoId) {
                agregarMensajeALaUI(message)
            }
        }
    }

    override fun onConnectionError(error: String) {
        runOnUiThread {
            // Manejo de errores silencioso
        }
    }

    private fun agregarMensajeALaUI(message: ChatMessage) {
        if (message.isMine) {
            agregarBurbuja(message.contenido, Gravity.END, 0xFF0D47A1.toInt(), 0xFFFFFFFF.toInt(), 0xFFB3E5FC.toInt())
        } else {
            agregarBurbuja(message.contenido, Gravity.START, 0xFFFFFFFF.toInt(), 0xFF1A1A1A.toInt(), 0xFF8A8A8A.toInt())
        }
    }

    private fun agregarBurbuja(texto: String, gravedad: Int, colorFondo: Int, colorTexto: Int, colorHora: Int) {
        val wrapper = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also { it.topMargin = dp(12) }
            orientation = LinearLayout.VERTICAL
            gravity = gravedad
        }

        val card = CardView(this).apply {
            radius = dp(16).toFloat()
            cardElevation = dp(2).toFloat()
            setCardBackgroundColor(colorFondo)
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
            text = texto
            textSize = 15f
            setTextColor(colorTexto)
        }

        val txtHora = TextView(this).apply {
            text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            textSize = 11f
            setTextColor(colorHora)
            gravity = Gravity.END
            setPadding(0, dp(4), 0, 0)
        }

        inner.addView(txtMsg)
        inner.addView(txtHora)
        card.addView(inner)
        wrapper.addView(card)

        contenedorMensajes.addView(wrapper)
        scrollToBottom()
    }

    private fun scrollToBottom() {
        val scroll = findViewById<ScrollView>(R.id.scr12Mensajes)
        scroll.post { scroll.fullScroll(View.FOCUS_DOWN) }
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onDestroy() {
        super.onDestroy()
        socketManager.disconnect()
    }
}
