package com.example.kotlin.examen.domain

import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.example.kotlin.examen.data.repositories.DatosHistoriaRepository

class ConsultarDatosHistoricosRequirement {
    private val DatosHistoriaRepository = DatosHistoriaRepository()

    operator fun invoke(callback: (List<EventoHistorico>?) -> Unit) {
        // Llama al método de consulta en el repositorio y pasa el resultado al callback.
        DatosHistoriaRepository.consultarEventosHistoricos { eventos ->
            callback(eventos)
        }
    }
}