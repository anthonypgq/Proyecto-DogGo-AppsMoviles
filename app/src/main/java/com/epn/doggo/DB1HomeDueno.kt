package com.epn.doggo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

// Data class para representar a un paseador
data class Paseador(val nombre: String, val rating: String, val paseos: String, val precio: String, val distancia: String)
private lateinit var duenoId: String

class HomeDuenio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_7home_duenio)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // ID DEL USUARIO EN USO
        duenoId = intent.getStringExtra("usuario_id")
            ?: run {
                Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_LONG).show()
                finish()
                return
            }

        // Datos de ejemplo
        val paseadores = listOf(
            Paseador("Carlos Rodríguez", "4.8", "15 paseos", "$20/hora", "0.8 km"),
            Paseador("Maria González", "4.9", "127 paseos", "$25/hora", "1.2 km"),
            Paseador("Ana Pérez", "4.7", "43 paseos", "$18/hora", "0.5 km")
        )

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PaseadorAdapter(paseadores)

        // Configurar BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    // Ya estás en la pantalla de inicio
                    true
                }
                R.id.nav_chat -> {
                    val intent = Intent(this, DB2ChatPaseador::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    true
                }
                R.id.nav_mascotas -> {
                    val intent = Intent(this, MascotasActivity::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    true
                }
                R.id.nav_perfil -> {
                    val intent = Intent(this, MiPerfilActivity::class.java)
                    intent.putExtra("usuario_id", duenoId)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}

// Adaptador para la lista de paseadores
class PaseadorAdapter(private val paseadores: List<Paseador>) : RecyclerView.Adapter<PaseadorAdapter.PaseadorViewHolder>() {

    class PaseadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val walkerName: TextView = itemView.findViewById(R.id.walker_name)
        val walkerRating: TextView = itemView.findViewById(R.id.walker_rating)
        val walkerWalks: TextView = itemView.findViewById(R.id.walker_walks)
        val walkerPrice: TextView = itemView.findViewById(R.id.walker_price)
        val walkerDistance: TextView = itemView.findViewById(R.id.walker_distance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaseadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_paseador, parent, false)
        return PaseadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaseadorViewHolder, position: Int) {
        val paseador = paseadores[position]
        holder.walkerName.text = paseador.nombre
        holder.walkerRating.text = paseador.rating
        holder.walkerWalks.text = paseador.paseos
        holder.walkerPrice.text = paseador.precio
        holder.walkerDistance.text = paseador.distancia

        // Configurar el click listener para abrir el perfil del paseador
        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, PerfilPaseador::class.java)
            // Opcional: Pasar datos del paseador a la actividad de perfil
            // intent.putExtra("NOMBRE_PASEADOR", paseador.nombre)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = paseadores.size
}