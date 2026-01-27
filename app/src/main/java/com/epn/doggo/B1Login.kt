package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class B1Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val layoutEmail = findViewById<TextInputLayout>(R.id.inputEmail)
        val layoutPsw = findViewById<TextInputLayout>(R.id.inputPsw)

        btnLogin.setOnClickListener {
            val user = findViewById<TextInputEditText>(R.id.txtEmail).text.toString()
            val password = findViewById<TextInputEditText>(R.id.txtPsw).text.toString()

            layoutEmail.error = null
            layoutPsw.error = null

            val request = LoginRequest(
                email = user,
                contrasena = password
            )

            ApiClient.api.login(request)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.isSuccessful) {
                            // LOGIN ACEPTADO POR LA API
                            val intent = Intent(this@B1Login, HomeDuenio::class.java)
                            startActivity(intent)
                            finishAffinity()
                        } else {
                            // LOGIN RECHAZADO POR LA API
                            layoutEmail.error = "Credenciales incorrectas"
                            layoutPsw.error = "Credenciales incorrectas"
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        layoutEmail.error = "No se pudo conectar con el servidor"
                    }
                })

//            if (user == "hola@hola.com" && password == "123") {
//                val intent = Intent(this, HomeDuenio::class.java)
//                startActivity(intent)
//                finishAffinity()
//            } else {
//                layoutEmail.error = "Usuario incorrecto"
//                layoutPsw.error = "Contraseña incorrecta"
//            }
        }
    }
}

