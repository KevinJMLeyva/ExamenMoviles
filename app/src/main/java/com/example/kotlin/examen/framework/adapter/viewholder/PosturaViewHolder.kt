package com.example.kotlin.examen.framework.adapter.viewholder

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.examen.data.network.model.Postura
import com.example.kotlin.examen.databinding.ItemPosturaBinding

/**
 * ViewHolder para mostrar los elementos de la lista de posturas en el RecyclerView.
 * Gestiona el enlace de los datos con las vistas de cada ítem del RecyclerView.
 *
 * @property binding Objeto de enlace de vista generado para el diseño de un ítem de postura (ItemPosturaBinding).
 */
class PosturaViewHolder(
    private val binding: ItemPosturaBinding,
) : RecyclerView.ViewHolder(binding.root) {
    /**
     * Asocia un objeto `Postura` con las vistas correspondientes en el ítem.
     *
     * @param postura El objeto `Postura` que contiene los datos a mostrar.
     * @param context El contexto necesario para cargar la imagen con Glide.
     */
    fun bind(
        postura: Postura,
        context: Context,
    ) {
        // Configura el nombre de la postura en el TextView
        binding.nombreTextView.text = postura.nombre

        // Usa Glide para cargar la imagen de la postura en el ImageView (IVPhoto)
        Glide
            .with(context)
            .load(postura.imagen) // Carga la imagen desde la URL proporcionada en el objeto Postura
            .into(binding.IVPhoto) // Asigna la imagen cargada al ImageView
    }
}
