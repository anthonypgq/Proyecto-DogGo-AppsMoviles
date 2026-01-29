package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.epn.doggo.data.DbHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DA2HorariosPaseador : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_da2_horarios_paseador)

        dbHelper = DbHelper(this)

        val btnFinish = findViewById<Button>(R.id.btnFinish)
        val txtZonaCobertura = findViewById<EditText>(R.id.txtZonaCobertura)

        // Recuperar datos de la pantalla anterior
        val name = intent.getStringExtra("name") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val address = intent.getStringExtra("address") ?: ""
        val bio = intent.getStringExtra("bio") ?: ""
        val rate = intent.getIntExtra("rate", 0)

        btnFinish.setOnClickListener {
            val zonaCobertura = txtZonaCobertura.text.toString().trim()

            if (zonaCobertura.isEmpty()) {
                txtZonaCobertura.error = "La zona de cobertura es obligatoria"
                return@setOnClickListener
            }

            // Crear el objeto de registro para el paseador
            val request = RegisterRequest(
                email = email,
                contrasena = password,
                nombre_completo = name,
                telefono = phone,
                direccion = address,
                rol = "paseador",
                biografia = bio,
                tarifa_hora = rate,
                zona_servicio = zonaCobertura
            )

            // Llamada a la API
            ApiClient.api.register(request).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        
                        // Guardar en SQLite para mantener la sesión
                        // Nota: RegisterResponse debe devolver el objeto UserApi o debemos construirlo
                        // Asumiendo que responseBody.data es el usuario creado
                        // dbHelper.saveUser(...) 

                        Toast.makeText(this@DA2HorariosPaseador, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                        
                        val intent = Intent(this@DA2HorariosPaseador, DashboardPaseador::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        Toast.makeText(this@DA2HorariosPaseador, "Error al registrar: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@DA2HorariosPaseador, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
