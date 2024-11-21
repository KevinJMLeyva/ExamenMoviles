package com.example.kotlin.examen.framework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.example.kotlin.examen.domain.ConsultarDatosHistoricosRequirement

class ConsultarHistoriaViewModel: ViewModel() {
    // LiveData privada para almacenar las posturas, accesible solo dentro de esta clase.
    private val _eventosLiveData = MutableLiveData<List<EventoHistorico>>()

    // LiveData pública para ser observada externamente sin exponer su capacidad de modificación.
    val eventosLiveData: LiveData<List<EventoHistorico>> get() = _eventosLiveData

    // Dependencia que permite la consulta de las posturas.
    private val consultarDatosHistoricosRequirement = ConsultarDatosHistoricosRequirement()

    /**
     * Método para consultar las posturas y actualizar el LiveData con los resultados obtenidos.
     * Utiliza `consultarPosturaRequirement` para obtener una lista de posturas y luego
     * actualiza `_posturasLiveData` con el resultado, permitiendo la observación de cambios.
     */
    fun consultarDatosHistoricos() {
        consultarDatosHistoricosRequirement { result ->
            _eventosLiveData.postValue(result ?: emptyList()) // Actualiza LiveData con el resultado o una lista vacía
        }
    }
}