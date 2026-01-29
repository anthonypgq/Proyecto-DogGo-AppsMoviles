package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.epn.doggo.data.DbHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MiPerfilPaseador : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private var paseadorId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mi_perfil_paseador)

        dbHelper = DbHelper(this)

        // Obtener ID del usuario desde SQLite o Intent
        paseadorId = intent.getStringExtra("usuario_id") ?: dbHelper.getUser()?.id ?: ""

        if (paseadorId.isEmpty()) {
            Toast.makeText(this, "Error: No se pudo identificar al usuario", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        
        val txtNombreValue = findViewById<TextView>(R.id.txtNombreValue)
        val txtEmailValue = findViewById<TextView>(R.id.txtEmailValue)
        val txtBioValue = findViewById<TextView>(R.id.txtBioValue)
        val txtRateValue = findViewById<TextView>(R.id.txtRateValue)

        val editIconNombre = findViewById<ImageView>(R.id.editIconNombre)
        val editIconEmail = findViewById<ImageView>(R.id.editIconEmail)
        val editIconBio = findViewById<ImageView>(R.id.editIconBio)
        val editIconRate = findViewById<ImageView>(R.id.editIconRate)

        bottomNavigationView.selectedItemId = R.id.nav_perfil

        btnLogout.setOnClickListener {
            cerrarSesion()
        }

        // Cargar datos desde la API
        cargarDatosPerfil(txtNombreValue, txtEmailValue, txtBioValue, txtRateValue)

        // Lógica de edición
        editIconNombre.setOnClickListener { mostrarDialogoEdicion("Nombre", txtNombreValue) }
        editIconEmail.setOnClickListener { mostrarDialogoEdicion("Correo", txtEmailValue) }
        editIconBio.setOnClickListener { mostrarDialogoEdicion("Biografía", txtBioValue) }
        editIconRate.setOnClickListener { mostrarDialogoEdicion("Tarifa por hora", txtRateValue) }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    startActivity(Intent(this, DashboardPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_paseos -> {
                    startActivity(Intent(this, Paseos::class.java))
                    finish()
                    true
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, ChatSolicitudesPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_perfil -> true
                else -> false
            }
        }
    }

    private fun cargarDatosPerfil(name: TextView, email: TextView, bio: TextView, rate: TextView) {
        ApiClient.api.getUsuario(paseadorId).enqueue(object : Callback<GetUsuarioResponse> {
            override fun onResponse(call: Call<GetUsuarioResponse>, response: Response<GetUsuarioResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val usuario = response.body()!!.data
                    name.text = usuario.nombre_completo
                    email.text = usuario.email
                    
                    // Datos específicos de paseador
                    bio.text = usuario.paseadores.biografia
                    rate.text = "$ ${usuario.paseadores.tarifa_hora}"
                } else {
                    Toast.makeText(this@MiPerfilPaseador, "No se pudo obtener el perfil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetUsuarioResponse>, t: Throwable) {
                Toast.makeText(this@MiPerfilPaseador, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cerrarSesion() {
        dbHelper.logout()
        val intent = Intent(this, A1Bienvenida::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun mostrarDialogoEdicion(titulo: String, textView: TextView) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar $titulo")

        val input = EditText(this)
        input.setText(textView.text.toString())
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val nuevoValor = input.text.toString()
            if (nuevoValor.isNotBlank()) {
                textView.text = nuevoValor
                Toast.makeText(this, "$titulo actualizado", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
