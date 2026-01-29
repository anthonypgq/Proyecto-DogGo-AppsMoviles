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

class MiPerfilActivity : AppCompatActivity() {

    private lateinit var duenoId: String
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_6mi_perfil)

        dbHelper = DbHelper(this)

        duenoId = intent.getStringExtra("usuario_id")
            ?: run {
                Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_LONG).show()
                finish()
                return
            }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        
        val valueName = findViewById<TextView>(R.id.valueName)
        val valueEmail = findViewById<TextView>(R.id.valueEmail)
        val valuePhone = findViewById<TextView>(R.id.valuePhone)
        val valueAddress = findViewById<TextView>(R.id.valueAddress)

        val editName = findViewById<ImageView>(R.id.editName)
        val editEmail = findViewById<ImageView>(R.id.editEmail)
        val editPhone = findViewById<ImageView>(R.id.editPhone)
        val editAddress = findViewById<ImageView>(R.id.editAddress)

        bottomNavigationView.selectedItemId = R.id.nav_perfil

        btnLogout.setOnClickListener {
            cerrarSesion()
        }

        ApiClient.api.getUsuario(duenoId)
            .enqueue(object : retrofit2.Callback<GetUsuarioResponse> {
                override fun onResponse(
                    call: retrofit2.Call<GetUsuarioResponse>,
                    response: retrofit2.Response<GetUsuarioResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val usuario = response.body()!!.data
                        valueName.text = usuario.nombre_completo
                        valueEmail.text = usuario.email
                        valuePhone.text = usuario.telefono
                        valueAddress.text = usuario.direccion

                        editName.setOnClickListener { mostrarDialogoEdicion("Nombre", valueName) }
                        editEmail.setOnClickListener { mostrarDialogoEdicion("Correo", valueEmail) }
                        editPhone.setOnClickListener { mostrarDialogoEdicion("Teléfono", valuePhone) }
                        editAddress.setOnClickListener { mostrarDialogoEdicion("Dirección", valueAddress) }

                    } else {
                        Toast.makeText(this@MiPerfilActivity, "No se pudo obtener el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<GetUsuarioResponse>, t: Throwable) {
                    Toast.makeText(this@MiPerfilActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            })

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    val intent = Intent(this, DB1HomeDuenio::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_chat -> {
                    val intent = Intent(this, DB2ChatPaseador::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_mascotas -> {
                    val intent = Intent(this, MascotasActivity::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_perfil -> true
                else -> false
            }
        }
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
