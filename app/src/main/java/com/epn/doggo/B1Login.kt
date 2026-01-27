package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.epn.doggo.data.DbHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class B1Login : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2login)

        dbHelper = DbHelper(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val layoutEmail = findViewById<TextInputLayout>(R.id.inputEmail)
        val layoutPsw = findViewById<TextInputLayout>(R.id.inputPsw)

        btnLogin.setOnClickListener {
            val user = findViewById<TextInputEditText>(R.id.txtEmail).text.toString()
            val password = findViewById<TextInputEditText>(R.id.txtPsw).text.toString()

            layoutEmail.error = null
            layoutPsw.error = null

            if (user.isEmpty() || password.isEmpty()) {
                if (user.isEmpty()) layoutEmail.error = "Ingrese su email"
                if (password.isEmpty()) layoutPsw.error = "Ingrese su contraseña"
                return@setOnClickListener
            }

            // LLAMADO DE LA API
            val request = LoginRequest(
                email = user,
                contrasena = password
            )

            ApiClient.api.login(request)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            // LOGIN ACEPTADO POR LA API
                            val userApi = response.body()!!.data
                            
                            // Guardar en SQLite
                            dbHelper.saveUser(userApi)

                            val duenoId = userApi.id
                            
                            // Redireccionar según el rol (asumiendo que hay HomeDuenio y DashboardPaseador)
                            val intent = if (userApi.rol == "Dueño") {
                                Intent(this@B1Login, DB1HomeDuenio::class.java)
                            } else {
                                Intent(this@B1Login, DashboardPaseador::class.java)
                            }
                            
                            intent.putExtra("usuario_id", duenoId)
                            startActivity(intent)
                            finishAffinity()
                        } else {
                            // LOGIN RECHAZADO POR LA API
                            layoutEmail.error = "Credenciales incorrectas"
                            layoutPsw.error = "Credenciales incorrectas"
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        layoutEmail.error = "No se pudo conectar con el servidor"
                    }
                })
        }
    }
}
