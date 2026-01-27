package com.epn.doggo

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val id: String,
    val dueno_id: String,
    val nombre: String,
    val raza: String,
    val edad: Int,
    val peso: Float,
    val tamano: String,
    val comportamiento: String
) : Parcelable

class PetAdapter(
    private val petList: List<Pet>,
    private val onEditClick: (Pet) -> Unit
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    class PetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView = view.findViewById(R.id.txtTitulo)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcion)
        val btnEditar: ImageView = view.findViewById(R.id.btnEditar)
    }

    // Get Mascotas
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PetViewHolder(layoutInflater.inflate(R.layout.list_item_pets, parent, false))
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = petList[position]
        holder.txtTitulo.text = pet.nombre
        holder.txtDescripcion.text = "${pet.raza} - ${pet.edad} - ${pet.tamano}"

        holder.btnEditar.setOnClickListener { onEditClick(pet) }
    }

    override fun getItemCount(): Int = petList.size
}
