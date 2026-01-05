package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DA2AnadirMascotas : AppCompatActivity() {

    private val listaMascotas = mutableListOf<Pet>()

    private lateinit var petAdapter: PetAdapter

    private val getPetResult = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val pet = result.data?.getParcelableExtra<Pet>("EXTRA_NEW_PET")
            pet?.let {
                listaMascotas.add(it)
                petAdapter.notifyDataSetChanged() // Refresca la lista visualmente
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_5aniadir_mascotas)

        val btnFinishAndNext = findViewById<Button>(R.id.btnFinishAndNExt)
        val btnAddPet = findViewById<Button>(R.id.btnAddPet)
        val recycler = findViewById<RecyclerView>(R.id.recyclerMascotas)

        // 2. Configurar el adaptador con la lista vacía
        petAdapter = PetAdapter(listaMascotas) { pet ->
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            startActivity(intent)
            aplicarAnimacionEntrada()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = petAdapter

        btnAddPet.setOnClickListener {
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            getPetResult.launch(intent) // Usamos el launcher en lugar de startActivity
            aplicarAnimacionEntrada()
        }

        btnFinishAndNext.setOnClickListener {
            // 3. LA VALIDACIÓN: Ahora sí impedirá el paso si no has agregado nada
            if (listaMascotas.isEmpty()) {
                Toast.makeText(this, "Debe añadir al menos una mascota para registrarse", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, HomeDuenio::class.java)
                startActivity(intent)
                aplicarAnimacionEntrada()
            }
        }
    }

    private fun aplicarAnimacionEntrada() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.slide_up, R.anim.stay)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_up, R.anim.stay)
        }
    }
}