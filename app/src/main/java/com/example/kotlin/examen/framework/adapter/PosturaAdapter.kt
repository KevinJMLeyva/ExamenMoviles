package com.example.kotlin.examen.framework.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.examen.data.network.model.Postura
import com.example.kotlin.examen.databinding.ItemPosturaBinding
import com.example.kotlin.examen.framework.adapter.viewholder.PosturaViewHolder
/**
 * Adaptador para mostrar una lista de objetos `Postura` en un `RecyclerView`.
 * Permite filtrar las posturas por su nombre.
 */
class PosturaAdapter() : RecyclerView.Adapter<PosturaViewHolder>() {

    // Lista de datos original (sin filtrar)
    private var datos: List<Postura> = emptyList()

    // Lista para almacenar los datos filtrados
    private var datosFiltrados: List<Postura> = emptyList()

    private lateinit var contexto: Context


    fun establecerPosturaData(
        basicData: List<Postura>,
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
                datos.filter { it.nombre.contains(query, ignoreCase = true) } // Filtra por nombre, ignorando mayúsculas/minúsculas
            }
        notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PosturaViewHolder {
        val binding = ItemPosturaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosturaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PosturaViewHolder, position: Int) {
        val item = datosFiltrados[position]
        holder.bind(item, contexto) // Pasa el objeto postura y contexto al ViewHolder
    }


    override fun getItemCount(): Int {
        return datosFiltrados.size // Devuelve el tamaño de los datos filtrados
    }
}
