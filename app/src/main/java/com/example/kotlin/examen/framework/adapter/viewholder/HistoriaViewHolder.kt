package com.example.kotlin.examen.framework.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.example.kotlin.examen.databinding.ItemHistoriaBinding
import com.example.kotlin.examen.databinding.ItemPosturaBinding

class HistoriaViewHolder(
    private val binding: ItemHistoriaBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(historia: EventoHistorico, context: Context) {
        // Configura el nombre de la postura en el TextView
        binding.dateTextView.text = historia.date
        binding.descriptionTextView.text = historia.description
    }
}
