package com.example.kotlin.examen.framework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.example.kotlin.examen.domain.ConsultarDatosHistoricosRequirement

class ConsultarHistoriaViewModel : ViewModel() {
    private val _eventosLiveData = MutableLiveData<List<EventoHistorico>>()
    val eventosLiveData: LiveData<List<EventoHistorico>> get() = _eventosLiveData

    private val _errorLiveData = MutableLiveData<String>() // Nuevo LiveData para errores
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private val consultarDatosHistoricosRequirement = ConsultarDatosHistoricosRequirement()

    fun consultarDatosHistoricos() {
        consultarDatosHistoricosRequirement(
            onSuccess = { result ->
                _eventosLiveData.postValue(result ?: emptyList()) // Actualiza LiveData con los eventos
            },
            onError = { error ->
                _errorLiveData.postValue(error) // Actualiza LiveData con el mensaje de error
            }
        )
    }
}
