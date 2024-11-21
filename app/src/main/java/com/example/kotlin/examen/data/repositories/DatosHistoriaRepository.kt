package com.example.kotlin.examen.data.repositories

import android.util.Log
import com.example.kotlin.examen.data.network.NetworkModuleDI
import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.parse.ParseException

/**
 * Repositorio que maneja la lógica de acceso a datos para los eventos históricos.
 */
class DatosHistoriaRepository {
    fun consultarEventosHistoricos(
        onSuccess: (List<EventoHistorico>?) -> Unit,
        onError: (String) -> Unit // Nuevo parámetro para manejar errores
    ) {
        val parametros = HashMap<String, Any>()

        NetworkModuleDI.callCloudFunction<HashMap<String, Any>>("hello", parametros) { resultado, e ->
            if (e == null) {
                try {
                    val gson = Gson()
                    val resultadoMap = resultado?.get("data") ?: emptyList<Map<String, Any>>()
                    val json = gson.toJson(resultadoMap)

                    val listaTipo = object : TypeToken<List<Map<String, Any>>>() {}.type
                    val eventosHistoricos = gson.fromJson<List<Map<String, Any>>>(json, listaTipo)

                    val eventos = eventosHistoricos.map {
                        EventoHistorico(
                            date = it["estimatedData"]?.let { it1 -> (it1 as Map<*, *>)["date"].toString() } ?: "",
                            description = it["estimatedData"]?.let { it1 -> (it1 as Map<*, *>)["description"].toString() } ?: "",
                            lang = it["estimatedData"]?.let { it1 -> (it1 as Map<*, *>)["lang"].toString() } ?: "",
                            category1 = it["estimatedData"]?.let { it1 -> (it1 as Map<*, *>)["category1"].toString() } ?: "",
                            category2 = it["estimatedData"]?.let { it1 -> (it1 as Map<*, *>)["category2"].toString() } ?: "",
                            granularity = it["estimatedData"]?.let { it1 -> (it1 as Map<*, *>)["granularity"].toString() } ?: "",
                            createdAt = it["state"]?.let { it1 -> (it1 as Map<*, *>)["createdAt"].toString() } ?: "",
                            updatedAt = it["state"]?.let { it1 -> (it1 as Map<*, *>)["updatedAt"].toString() } ?: "",
                            objectId = it["state"]?.let { it1 -> (it1 as Map<*, *>)["objectId"].toString() } ?: ""
                        )
                    }
                    onSuccess(eventos)
                } catch (ex: Exception) {
                    onError("Error al procesar los datos: ${ex.message}")
                }
            } else {
                val errorMessage = when {
                    e is ParseException && e.code == ParseException.CONNECTION_FAILED -> "Error de conexión: ${e.message}"
                    e is ParseException && e.code == ParseException.OBJECT_NOT_FOUND -> "Datos no encontrados: ${e.message}"
                    else -> "Error desconocido: ${e.message}"
                }
                onError(errorMessage)
            }
        }
    }
}
