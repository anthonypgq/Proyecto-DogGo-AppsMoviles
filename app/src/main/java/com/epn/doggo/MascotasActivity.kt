package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MascotasActivity : AppCompatActivity() {

    private lateinit var petAdapter: PetAdapter
    private val listaMascotas = mutableListOf<Pet>()

    private val petLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult

        // Nuevo
        val nuevaMascota = result.data?.getParcelableExtra<Pet>(DA2AnadirMascotas.EXTRA_NEW_PET)
        if (nuevaMascota != null) {
            listaMascotas.add(nuevaMascota)
            petAdapter.notifyDataSetChanged()
            return@registerForActivityResult
        }

        // Editada
        val mascotaEditada = result.data?.getParcelableExtra<Pet>(DA2AnadirMascotas.EXTRA_UPDATED_PET)
        if (mascotaEditada != null) {
            val idx = listaMascotas.indexOfFirst { it.id == mascotaEditada.id }
            if (idx != -1) {
                listaMascotas[idx] = mascotaEditada
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

        // Datos de ejemplo (actualizados con id + comportamiento)
        if (listaMascotas.isEmpty()) {
            listaMascotas.add(Pet("1","1", "Luna", "Labrador", 2, 20.0f, "Mediana", "Tranquila"))
            listaMascotas.add(Pet("2","2", "Max", "Pastor Alemán", 1, 30.0f, "Grande", "Juguetón"))
            listaMascotas.add(Pet("3","3", "Kiara", "Beagle", 3, 10.0f, "Pequeña", "Sociable"))
        }

        petAdapter = PetAdapter(listaMascotas) { pet ->
            // ✅ EDITAR (precarga)
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            intent.putExtra(DA2AnadirMascotas.EXTRA_PET_TO_EDIT, pet)
            petLauncher.launch(intent)
        }

        recyclerMascotas.layoutManager = LinearLayoutManager(this)
        recyclerMascotas.adapter = petAdapter

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
