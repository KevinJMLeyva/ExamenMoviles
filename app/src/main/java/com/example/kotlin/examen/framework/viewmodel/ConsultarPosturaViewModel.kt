package com.example.kotlin.examen.framework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.examen.data.network.model.Postura
import com.example.kotlin.examen.domain.ConsultarPosturaRequirement

/**
 * ViewModel para gestionar la lógica de la consulta de posturas.
 *
 * @property posturasLiveData Objeto LiveData que contiene una lista de posturas,
 * permitiendo a los observadores reaccionar a los cambios de datos.
 */
class ConsultarPosturaViewModel : ViewModel() {

    // LiveData privada para almacenar las posturas, accesible solo dentro de esta clase.
    private val _posturasLiveData = MutableLiveData<List<Postura>>()

    // LiveData pública para ser observada externamente sin exponer su capacidad de modificación.
    val posturasLiveData: LiveData<List<Postura>> get() = _posturasLiveData

    // Dependencia que permite la consulta de las posturas.
    private val consultarPosturaRequirement = ConsultarPosturaRequirement()

    /**
     * Método para consultar las posturas y actualizar el LiveData con los resultados obtenidos.
     * Utiliza `consultarPosturaRequirement` para obtener una lista de posturas y luego
     * actualiza `_posturasLiveData` con el resultado, permitiendo la observación de cambios.
     */
    fun consultarPosturas() {
        consultarPosturaRequirement { result ->
            _posturasLiveData.postValue(result ?: emptyList()) // Actualiza LiveData con el resultado o una lista vacía
        }
    }
}
