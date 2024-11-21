package com.example.kotlin.examen.domain

import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.example.kotlin.examen.data.repositories.DatosHistoriaRepository

class ConsultarDatosHistoricosRequirement {
    private val datosHistoriaRepository = DatosHistoriaRepository()

    operator fun invoke(
        onSuccess: (List<EventoHistorico>?) -> Unit,
        onError: (String) -> Unit // Nuevo parámetro para manejar errores
    ) {
        // Llama al método de consulta en el repositorio y pasa los callbacks
        datosHistoriaRepository.consultarEventosHistoricos(
            onSuccess = { eventos ->
                onSuccess(eventos)
            },
            onError = { error ->
                onError(error)
            }
        )
    }
}
