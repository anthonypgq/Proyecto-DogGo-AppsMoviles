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

    // Launcher para recibir el resultado de DA3AnadirNuevaMascota
    private val addPetLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val nuevaMascota = result.data?.getParcelableExtra<Pet>("EXTRA_NEW_PET")
            nuevaMascota?.let {
                listaMascotas.add(it)
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

        // Configurar selección actual en el menú
        bottomNavigationView.selectedItemId = R.id.nav_mascotas

        // Datos iniciales de ejemplo
        if (listaMascotas.isEmpty()) {
            listaMascotas.add(Pet("Luna", "Labrador", 2, "Mediana", 20.0f))
            listaMascotas.add(Pet("Max", "Pastor Alemán", 1, "Grande", 30.0f))
            listaMascotas.add(Pet("Kiara", "Beagle", 3, "Pequeña", 10.0f))
        }

        petAdapter = PetAdapter(listaMascotas) { pet ->
            // Acción opcional para editar
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            startActivity(intent)
        }

        recyclerMascotas.layoutManager = LinearLayoutManager(this)
        recyclerMascotas.adapter = petAdapter

        btnAddNewPet.setOnClickListener {
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            addPetLauncher.launch(intent)
        }

        // Configurar navegación
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