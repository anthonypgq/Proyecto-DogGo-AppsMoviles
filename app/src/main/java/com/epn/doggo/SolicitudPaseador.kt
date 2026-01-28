package com.epn.doggo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
private lateinit var paseadorId: String
private lateinit var duenoId: String
class SolicitudPaseador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_9solicitud_paseador)

        // ID DEL USUARIO EN USO
        paseadorId = intent.getStringExtra("paseador_id")
            ?: run {
                Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_LONG).show()
                finish()
                return
            }
        paseadorId = intent.getStringExtra("usuario_id")
            ?: run {
                Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_LONG).show()
                finish()
                return
            }

        // --- Controles --- 
        val backButton = findViewById<ImageView>(R.id.btn9Back)
        val mascotaSpinner = findViewById<Spinner>(R.id.spn9Mascota)
        val fechaEditText = findViewById<EditText>(R.id.txt9Fecha)
        val horaEditText = findViewById<EditText>(R.id.txt9Hora)
        val confirmarButton = findViewById<Button>(R.id.btn9Confirmar)

        // LLAMADA A LA API
//        val request = SolicitarPaseoRequest(
//            paseador_id = paseadorId,
//            dueno_id = duenoId,
//            mascota_id = mascotaId,
//            fecha = "2025-01-27",
//            hora_inicio = "23:25:46.600Z",
//            duracion_minutos = 60,
//            estado = "por_confirmar",
//            notas = "Paseo por el parque",
//            precio_total = paseadorSeleccionado.tarifaHora * 1
//        )
//        ApiClient.api.solicitarPaseo(request)
//            .enqueue(object : Callback<SolicitarPaseoResponse> {
//
//                override fun onResponse(
//                    call: Call<SolicitarPaseoResponse>,
//                    response: Response<SolicitarPaseoResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//
//                        Toast.makeText(
//                            this@TuActivity,
//                            "Paseo solicitado correctamente",
//                            Toast.LENGTH_LONG
//                        ).show()
//
//                        finish() // o ir a otra pantalla
//                    } else {
//                        Toast.makeText(
//                            this@TuActivity,
//                            "Error al solicitar el paseo",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<SolicitarPaseoResponse>, t: Throwable) {
//                    Toast.makeText(
//                        this@TuActivity,
//                        "No se pudo conectar al servidor",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })

        // FINALIZACION LLAMADA A LA API

        // --- Configuración del Spinner de Mascotas ---
        val mascotas = listOf("Luna (Labrador, Mediana)", "Rocky (Beagle, Pequeño)", "Max (Golden, Grande)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mascotas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mascotaSpinner.adapter = adapter

        // --- Configuración del Selector de Fecha ---
        fechaEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd 'de' MMMM, yyyy", Locale("es", "ES"))
                    fechaEditText.setText(dateFormat.format(selectedDate.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        // --- Configuración del Selector de Hora ---
        horaEditText.setOnClickListener { 
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    val timeFormat = SimpleDateFormat("h:mm a", Locale.US)
                    horaEditText.setText(timeFormat.format(selectedTime.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false // Usar formato de 12 horas
            )
            timePickerDialog.show()
        }

        // --- Botón de Confirmación ---
        confirmarButton.setOnClickListener {
            val intent = Intent(this, DB1HomeDuenio::class.java)
            intent.putExtra("usuario_id", duenoId)
            startActivity(intent)
        }

        // --- Botón de Retroceso ---
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}