package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MascotasActivity : AppCompatActivity() {

    private lateinit var petAdapter: PetAdapter
    private val listaMascotas = mutableListOf<Pet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mascotas)

        val rvMascotas = findViewById<RecyclerView>(R.id.rvMascotas)
        val fabAddPet = findViewById<FloatingActionButton>(R.id.fabAddPet)

        // Datos de ejemplo para visualizar la lista
        listaMascotas.add(Pet("Luna", "Labrador", 2, "Mediana", 20.0f))
        listaMascotas.add(Pet("Max", "Golden Retriever", 4, "Grande", 30.0f))
        listaMascotas.add(Pet("Bella", "Beagle", 1, "Pequeña", 10.0f))

        petAdapter = PetAdapter(listaMascotas) { pet ->
            // Acción al editar mascota (opcional por ahora)
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            startActivity(intent)
        }

        rvMascotas.layoutManager = LinearLayoutManager(this)
        rvMascotas.adapter = petAdapter

        fabAddPet.setOnClickListener {
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            startActivity(intent)
        }
    }
}