package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MascotasActivity : AppCompatActivity() {

    private val mascotas = mutableListOf<Pet>()
    private lateinit var petAdapter: PetAdapter

    private val petLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult

        //Actualizar lista
        // Nuevo
        val nuevaMascota = result.data?.getParcelableExtra<Pet>(DA2AnadirMascotas.EXTRA_NEW_PET)
        if (nuevaMascota != null) {
            mascotas.add(nuevaMascota)
            petAdapter.notifyDataSetChanged()
            return@registerForActivityResult
        }

        // Editada
        val mascotaEditada = result.data?.getParcelableExtra<Pet>(DA2AnadirMascotas.EXTRA_UPDATED_PET)
        if (mascotaEditada != null) {
            val idx = mascotas.indexOfFirst { it.id == mascotaEditada.id }
            if (idx != -1) {
                mascotas[idx] = mascotaEditada
                petAdapter.notifyItemChanged(idx)
            } else {
                petAdapter.notifyDataSetChanged()
            }
        }
    }
    private lateinit var duenoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mascotas)

        val btnAddNewPet = findViewById<Button>(R.id.btnAddNewPet)
        val recyclerMascotas = findViewById<RecyclerView>(R.id.recyclerMascotas)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // ID DEL USUARIO EN USO
        duenoId = intent.getStringExtra("usuario_id")
            ?: run {
                Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_LONG).show()
                finish()
                return
            }

        bottomNavigationView.selectedItemId = R.id.nav_mascotas

        // LLAMADO AL API
        ApiClient.api.getMascotasPorDueno(duenoId)
            .enqueue(object : Callback<MascotasPorDuenoResponse> {

                override fun onResponse(
                    call: Call<MascotasPorDuenoResponse>,
                    response: Response<MascotasPorDuenoResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        mascotas.clear()
                        mascotas.addAll(
                            response.body()!!.data.map { apiMascota ->
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

                        petAdapter = PetAdapter(mascotas) { pet ->
                            val intent = Intent(this@MascotasActivity, DA3AnadirNuevaMascota::class.java)
                            intent.putExtra("usuario_id", duenoId)
                            intent.putExtra("pet_id", pet.id)
                            intent.putExtra(DA2AnadirMascotas.EXTRA_PET_TO_EDIT, pet)
                            petLauncher.launch(intent)
                        }

                        // Configurar RecyclerView
                        recyclerMascotas.layoutManager = LinearLayoutManager(this@MascotasActivity)
                        recyclerMascotas.adapter = petAdapter

                    } else {
                        Toast.makeText(
                            this@MascotasActivity,
                            "Error al cargar mascotas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<MascotasPorDuenoResponse>, t: Throwable) {
                    Toast.makeText(
                        this@MascotasActivity,
                        "No se pudo conectar al servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        // FINALIZA LLAMADO AL API

        btnAddNewPet.setOnClickListener {
            // ✅ NUEVO
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            intent.putExtra("usuario_id", duenoId)
            petLauncher.launch(intent)
        }

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
                R.id.nav_mascotas -> true
                R.id.nav_perfil -> {
                    val intent = Intent(this, MiPerfilActivity::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}

