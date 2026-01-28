package com.epn.doggo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

private lateinit var PaseadorId: String
private lateinit var duenoId: String

class PerfilPaseador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_8perfil_paseador)

        // ID DEL USUARIO EN USO
        PaseadorId = intent.getStringExtra("paseador_id")
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
        val valueBio = findViewById<TextView>(R.id.lbl8Biografia)
        val valueNombre = findViewById<TextView>(R.id.lbl8NombrePaseador)
        val backButton = findViewById<ImageView>(R.id.btn8Back)
        val solicitarButton = findViewById<Button>(R.id.btn8SolicitarPaseo)

        ApiClient.api.getUsuario(PaseadorId)
            .enqueue(object : retrofit2.Callback<GetUsuarioResponse> {
                override fun onResponse(
                    call: retrofit2.Call<GetUsuarioResponse>,
                    response: retrofit2.Response<GetUsuarioResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val usuario = response.body()!!.data
                        // AQUÍ YA TIENES LOS VALORES
                        valueBio.text = usuario.paseadores.biografia
                        valueNombre.text = usuario.nombre_completo

                        backButton.setOnClickListener { finish() }

                        solicitarButton.setOnClickListener {
                            val intent = Intent(this@PerfilPaseador, SolicitudPaseador::class.java)
                            intent.putExtra("paseador_id", PaseadorId)
                            intent.putExtra("usuario_id", duenoId)
                            startActivity(intent)
                        }

                    } else {
                        Toast.makeText(
                            this@PerfilPaseador,
                            "No se pudo obtener el usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(
                    call: retrofit2.Call<GetUsuarioResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@PerfilPaseador,
                        "Error de conexión",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })


    }
}