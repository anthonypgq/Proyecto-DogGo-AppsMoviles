package com.epn.doggo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DA2AnadirMascotas : AppCompatActivity() {

    companion object {
        const val EXTRA_NEW_PET = "EXTRA_NEW_PET"
        const val EXTRA_PET_TO_EDIT = "EXTRA_PET_TO_EDIT"
        const val EXTRA_UPDATED_PET = "EXTRA_UPDATED_PET"
    }

    private val listaMascotas = mutableListOf<Pet>()
    private lateinit var petAdapter: PetAdapter

    private var nextPetId = 1

    private val getPetResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult

        // A) Nuevo
        val newPet = result.data?.getParcelableExtra<Pet>(EXTRA_NEW_PET)
        if (newPet != null) {
            listaMascotas.add(newPet)
            petAdapter.notifyDataSetChanged()
            // aseguramos que el siguiente id sea único
            nextPetId = maxOf(nextPetId, newPet.id + 1)
            return@registerForActivityResult
        }

        // B) Actualizado
        val updatedPet = result.data?.getParcelableExtra<Pet>(EXTRA_UPDATED_PET)
        if (updatedPet != null) {
            val idx = listaMascotas.indexOfFirst { it.id == updatedPet.id }
            if (idx != -1) {
                listaMascotas[idx] = updatedPet
                petAdapter.notifyItemChanged(idx)
            } else {
                petAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_5aniadir_mascotas)
        applyInsets()

        val btnFinishAndNext = findViewById<Button>(R.id.btnFinishAndNExt)
        val btnAddPet = findViewById<Button>(R.id.btnAddPet)
        val recycler = findViewById<RecyclerView>(R.id.recyclerMascotas)

        petAdapter = PetAdapter(listaMascotas) { pet ->
            // ✅ EDITAR: enviamos la mascota y esperamos resultado
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            intent.putExtra(EXTRA_PET_TO_EDIT, pet)
            getPetResult.launch(intent)
            aplicarAnimacionEntrada()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = petAdapter

        btnAddPet.setOnClickListener {
            // ✅ NUEVO: mandamos el nextPetId para crear con id único
            val intent = Intent(this, DA3AnadirNuevaMascota::class.java)
            intent.putExtra("EXTRA_NEXT_ID", nextPetId)
            getPetResult.launch(intent)
            aplicarAnimacionEntrada()
        }

        btnFinishAndNext.setOnClickListener {
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

    private fun applyInsets() {
        val root = findViewById<View>(R.id.root)
        val topHeader = findViewById<View>(R.id.topHeader)
        val bottomSection = findViewById<View>(R.id.bottomSection)

        val topPad = topHeader.paddingTop
        val bottomPad = bottomSection.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Empuja el header hacia abajo (status bar)
            topHeader.setPadding(
                topHeader.paddingLeft,
                topPad + sysBars.top,
                topHeader.paddingRight,
                topHeader.paddingBottom
            )

            // Empuja el bottom hacia arriba (navigation bar)
            bottomSection.setPadding(
                bottomSection.paddingLeft,
                bottomSection.paddingTop,
                bottomSection.paddingRight,
                bottomPad + sysBars.bottom
            )

            insets
        }

        ViewCompat.requestApplyInsets(root)
    }
}
