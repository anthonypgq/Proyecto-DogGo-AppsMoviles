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
import kotlin.random.Random

// Data class para representar a un paseador
data class Paseador(
    val id: String,
    val nombre: String,
    val telefono: String,
    val email: String,
    val zona: String,
    val tarifaHora: Double,
    val biografia: String)
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

        // LLAMADA AL API
        ApiClient.api.getPaseadores()
            .enqueue(object : retrofit2.Callback<GetPaseadoresResponse> {
                override fun onResponse(
                    call: retrofit2.Call<GetPaseadoresResponse>,
                    response: retrofit2.Response<GetPaseadoresResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseApi = response.body()!!.data

                        // Ejemplo: recorrerlos
                        val paseadores = responseApi.map { apiPaseador ->
                            Paseador(
                                id = apiPaseador.id,
                                nombre = apiPaseador.nombre_completo,
                                telefono = apiPaseador.telefono,
                                email = apiPaseador.email,
                                zona = apiPaseador.paseadores.zona_servicio,
                                tarifaHora = apiPaseador.paseadores.tarifa_hora,
                                biografia = apiPaseador.paseadores.biografia
                            )
                        }

                        // Configurar RecyclerView
                        recyclerView.layoutManager = LinearLayoutManager(this@HomeDuenio)
                        recyclerView.adapter = PaseadorAdapter(paseadores)

                        // 👉 Aquí normalmente:
                        // - setear RecyclerView
                        // - pasar la lista al adapter
                    } else {
                        Toast.makeText(
                            this@HomeDuenio,
                            "Error al obtener paseadores",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(
                    call: retrofit2.Call<GetPaseadoresResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@HomeDuenio,
                        "No se pudo conectar al servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        // FINALIZAR LLAMADA AL API

        // Datos de ejemplo
//        val paseadores = listOf(
//            Paseador("Carlos Rodríguez", "4.8", "15 paseos", "$20/hora", "0.8 km"),
//            Paseador("Maria González", "4.9", "127 paseos", "$25/hora", "1.2 km"),
//            Paseador("Ana Pérez", "4.7", "43 paseos", "$18/hora", "0.5 km")
//        )

        // Configurar RecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = PaseadorAdapter(paseadores)

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
        holder.walkerRating.text = listOf(3, 3.5, 4, 4.5).random().toString()
        holder.walkerWalks.text = Random.nextInt(2,15).toString() +  "paseos"
        holder.walkerPrice.text = "$" + paseador.tarifaHora.toString() + "/hora"
        holder.walkerDistance.text = "%.1f".format(Random.nextDouble(0.2, 2.0)) + " km"

        // Configurar el click listener para abrir el perfil del paseador
        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, PerfilPaseador::class.java)
            intent.putExtra("paseador_id", paseador.id)
            // Opcional: Pasar datos del paseador a la actividad de perfil
            // intent.putExtra("NOMBRE_PASEADOR", paseador.nombre)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = paseadores.size
}