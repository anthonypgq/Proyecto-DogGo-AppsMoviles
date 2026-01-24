package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MascotasActivity : AppCompatActivity() {

    private lateinit var petAdapter: PetAdapter
    private val listaMascotas = mutableListOf<Pet>()
    private var nextPetId = 1

    private val petLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult

        // Nuevo
        val nuevaMascota = result.data?.getParcelableExtra<Pet>(DA2AnadirMascotas.EXTRA_NEW_PET)
        if (nuevaMascota != null) {
            listaMascotas.add(nuevaMascota)
            petAdapter.notifyDataSetChanged()
            nextPetId = maxOf(nextPetId, nuevaMascota.id + 1)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mascotas)

        val btnAddNewPet = findViewById<Button>(R.id.btnAddNewPet)
        val recyclerMascotas = findViewById<RecyclerView>(R.id.recyclerMascotas)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.selectedItemId = R.id.nav_mascotas

        // Datos de ejemplo (actualizados con id + comportamiento)
        if (listaMascotas.isEmpty()) {
            listaMascotas.add(Pet(nextPetId++, "Luna", "Labrador", 2, "Mediana", 20.0f, "Tranquila"))
            listaMascotas.add(Pet(nextPetId++, "Max", "Pastor Alemán", 1, "Grande", 30.0f, "Juguetón"))
            listaMascotas.add(Pet(nextPetId++, "Kiara", "Beagle", 3, "Pequeña", 10.0f, "Sociable"))
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
            intent.putExtra("EXTRA_NEXT_ID", nextPetId)
            petLauncher.launch(intent)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    startActivity(Intent(this, HomeDuenio::class.java))
                    finish()
                    true
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, DB2ChatPaseador::class.java))
                    finish()
                    true
                }
                R.id.nav_mascotas -> true
                R.id.nav_perfil -> {
                    startActivity(Intent(this, MiPerfilActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
