package com.epn.doggo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SolicitudPaseador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_9solicitud_paseador)

        // --- Controles --- 
        val backButton = findViewById<ImageView>(R.id.btn9Back)
        val mascotaSpinner = findViewById<Spinner>(R.id.spn9Mascota)
        val fechaEditText = findViewById<EditText>(R.id.txt9Fecha)
        val horaEditText = findViewById<EditText>(R.id.txt9Hora)
        val confirmarButton = findViewById<Button>(R.id.btn9Confirmar)

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
            
            val intent = Intent(this, HomeDuenio::class.java)
            startActivity(intent)
        }

        // --- Botón de Retroceso ---
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}