package com.example.kotlin.examen.framework.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.example.kotlin.examen.databinding.ItemHistoriaBinding
import com.example.kotlin.examen.databinding.ItemPosturaBinding
import com.example.kotlin.examen.framework.adapter.viewholder.HistoriaViewHolder
import com.example.kotlin.examen.framework.adapter.viewholder.PosturaViewHolder

class HistoriaAdapter() : RecyclerView.Adapter<HistoriaViewHolder>() {
    private var datos: List<EventoHistorico> = emptyList()

    // Lista para almacenar los datos filtrados
    private var datosFiltrados: List<EventoHistorico> = emptyList()

    private lateinit var contexto: Context

    fun establecerPosturaData(
        basicData: List<EventoHistorico>,
        contexto: Context,
    ) {
        this.datos = basicData
        this.datosFiltrados = basicData // Al principio, no hay filtro, por lo que se muestra toda la lista
        this.contexto = contexto
        notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
    }

    fun filtro(query: String) {
        datosFiltrados =
            if (query.isEmpty()) {
                datos // Si la consulta está vacía, muestra todos los datos
            } else {
                datos.filter { it.date.contains(query, ignoreCase = true) } // Filtra por nombre, ignorando mayúsculas/minúsculas
            }
        notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoriaViewHolder {
        val binding = ItemHistoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder:  HistoriaViewHolder, position: Int) {
        val item = datosFiltrados[position]
        holder.bind(item, contexto) // Pasa el objeto postura y contexto al ViewHolder
    }


    override fun getItemCount(): Int {
        return datosFiltrados.size // Devuelve el tamaño de los datos filtrados
    }
}