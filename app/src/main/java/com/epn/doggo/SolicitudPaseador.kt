package com.epn.doggo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
private lateinit var paseadorId: String
private lateinit var duenoId: String
private val mascotas = mutableListOf<Pet>()
private var mascotaId: String = ""
private var fecha: String = ""
private var horaInicio: String = ""
private lateinit var tarifaHora: String


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
        duenoId = intent.getStringExtra("usuario_id")
            ?: run {
                Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_LONG).show()
                finish()
                return
            }
        tarifaHora = intent.getStringExtra("paseador_tarifaHora").toString()

        // --- Controles --- 
        val backButton = findViewById<ImageView>(R.id.btn9Back)
        val mascotaSpinner = findViewById<Spinner>(R.id.spn9Mascota)
        val fechaEditText = findViewById<EditText>(R.id.txt9Fecha)
        val horaEditText = findViewById<EditText>(R.id.txt9Hora)
        val confirmarButton = findViewById<Button>(R.id.btn9Confirmar)
        val notasText = findViewById<EditText>(R.id.txt9Notas)
        val chipGroupDuracion = findViewById<ChipGroup>(R.id.chipGroupDuracion)

        // LLAMADA A LA API mascota
        ApiClient.api.getMascotasPorDueno(duenoId)
            .enqueue(object : Callback<MascotasPorDuenoResponse> {

                override fun onResponse(
                    call: Call<MascotasPorDuenoResponse>,
                    response: Response<MascotasPorDuenoResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        mascotas.addAll(response.body()!!.data.map { apiMascota ->
                            Pet(
                                id = apiMascota.id,
                                dueno_id = apiMascota.dueno_id,
                                nombre = apiMascota.nombre,
                                raza = apiMascota.raza,
                                edad = apiMascota.edad,
                                peso = apiMascota.peso,
                                tamano = apiMascota.tamano,
                                comportamiento = apiMascota.comportamiento
                            )
                        }
                        )
                        // --- Configuración del Spinner de Mascotas ---
                        val mascotasSpinner = mascotas.map { pet -> "${pet.nombre} (${pet.raza}, ${pet.tamano})" }
                        val adapter = ArrayAdapter(this@SolicitudPaseador, android.R.layout.simple_spinner_item, mascotasSpinner)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        mascotaSpinner.adapter = adapter

                        if (mascotas.isNotEmpty()) {
                            mascotaSpinner.setSelection(0)
                            mascotaId = mascotas[0].id
                        }

                    } else {
                        Toast.makeText(
                            this@SolicitudPaseador,
                            "Error al cargar mascotas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<MascotasPorDuenoResponse>, t: Throwable) {
                    Toast.makeText(
                        this@SolicitudPaseador,
                        "No se pudo conectar al servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        // --- Listeners: SOLO guardan estado (se configuran una vez) ---
        mascotaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // protege por si lista aún vacía
                if (position in mascotas.indices) {
                    mascotaId = mascotas[position].id
                } else {
                    mascotaId = ""
                }
                Log.d("DEBUG_PASEO", "onItemSelected mascotaId=$mascotaId")
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                mascotaId = ""
            }
        }

        fechaEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                val c = Calendar.getInstance().apply { set(y, m, d) }
                fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.time)
                fechaEditText.setText(SimpleDateFormat("dd 'de' MMMM, yyyy", Locale("es", "ES")).format(c.time))
                Log.d("DEBUG_PASEO", "fecha seleccionada = $fecha")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        horaEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { _, h, m ->
                val c = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, h)
                    set(Calendar.MINUTE, m)
                }
                horaInicio = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(c.time)
                horaEditText.setText(SimpleDateFormat("h:mm a", Locale.US).format(c.time))
                Log.d("DEBUG_PASEO", "hora seleccionada = $horaInicio")
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
        }



        // --- Botón de Confirmación ---
        confirmarButton.setOnClickListener {

            // LLAMADO API SOLICITAR PASEO
            // Leer campos dinámicos aquí (no fuera)
            val notas = notasText.text.toString().trim()

            if (mascotaId.isBlank()) {
                Toast.makeText(this, "Selecciona una mascota", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (fecha.isBlank()) {
                Toast.makeText(this, "Selecciona una fecha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (horaInicio.isBlank()) {
                Toast.makeText(this, "Selecciona una hora", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val duracionTexto = chipGroupDuracion.checkedChipId
                .takeIf { it != View.NO_ID }
                ?.let { id -> findViewById<Chip>(id).text.toString() }
                ?: run {
                    Toast.makeText(this, "Selecciona una duración", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            val duracionMinutos = when {
                duracionTexto.contains("30") -> 30
                duracionTexto.contains("1.5") -> 90
                duracionTexto.contains("2") -> 120
                else -> 60
            }

            // Logs de verificación (mira Logcat)
            //Log.d("DEBUG_PASEO", "mascotaId=$mascotaId fecha=$fecha hora=$horaInicio duracion=$duracionMinutos notas=$notas")

            val tarifa = tarifaHora.toDoubleOrNull() ?: 0.0
            val precioTotal = tarifa * (duracionMinutos / 60.0)
            /////////////////////////////////////
            Log.d("REQUEST_PASEO", """
                dueno_id=$duenoId
                mascota_id=$mascotaId
                paseador_id=$paseadorId
                fecha=$fecha
                hora_inicio=$horaInicio
                duracion=$duracionMinutos
                notas=$notas
                precio=$precioTotal
                """.trimIndent())

            val request = SolicitarPaseoRequest(
                paseador_id = paseadorId.toString(),
                dueno_id = duenoId.toString(),
                mascota_id = mascotaId.toString(),
                fecha = fecha.toString(),
                hora_inicio = horaInicio.toString(),
                duracion_minutos = duracionMinutos.toInt(),
                estado = "por confirmar",
                notas = notas.toString(),
                precio_total = precioTotal.toDouble()
            )
            ApiClient.api.solicitarPaseo(request)
                .enqueue(object : Callback<SolicitarPaseoResponse> {

                    override fun onResponse(
                        call: Call<SolicitarPaseoResponse>,
                        response: Response<SolicitarPaseoResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {

                            Toast.makeText(
                                this@SolicitudPaseador,
                                "Paseo solicitado correctamente",
                                Toast.LENGTH_LONG
                            ).show()

                            val intent = Intent(this@SolicitudPaseador, DB1HomeDuenio::class.java)
                            intent.putExtra("usuario_id", duenoId)
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this@SolicitudPaseador,
                                "Error al solicitar el paseo",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<SolicitarPaseoResponse>, t: Throwable) {
                        Toast.makeText(
                            this@SolicitudPaseador,
                            "No se pudo conectar al servidor",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

            // FINALIZACION LLAMADA A LA API
        }

        // --- Botón de Retroceso ---
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}