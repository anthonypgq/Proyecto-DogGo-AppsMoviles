package com.epn.doggo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class PaseoSolicitud(
    val nombrePerro: String,
    val detalle: String,
    val precio: String,
    val esHistorial: Boolean = false
)

class PaseoAdapter(
    private val lista: List<PaseoSolicitud>,
    private val onVerClick: (PaseoSolicitud) -> Unit = {}
) : RecyclerView.Adapter<PaseoAdapter.PaseoViewHolder>() {

    class PaseoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombrePerro)
        val txtDetalle: TextView = view.findViewById(R.id.txtDetallePaseo)
        val txtPrecio: TextView = view.findViewById(R.id.txtPrecioPaseo)
        val layoutAcciones: LinearLayout = view.findViewById(R.id.layoutAcciones)
        val btnVer: Button = view.findViewById(R.id.btnVer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaseoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_solicitud_paseo, parent, false)
        return PaseoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaseoViewHolder, position: Int) {
        val item = lista[position]
        holder.txtNombre.text = item.nombrePerro
        holder.txtDetalle.text = item.detalle
        holder.txtPrecio.text = item.precio

        if (item.esHistorial) {
            holder.layoutAcciones.visibility = View.GONE
        } else {
            holder.layoutAcciones.visibility = View.VISIBLE
            holder.btnVer.setOnClickListener { onVerClick(item) }
        }
    }

    override fun getItemCount(): Int = lista.size
}