package com.example.kotlin.historical.data.repositories

import android.util.Log
import com.example.kotlin.examen.data.network.NetworkModuleDI
import com.example.kotlin.examen.data.network.model.EventoHistorico
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.parse.ParseException

/**
 * Repositorio que maneja la lógica de acceso a datos para los eventos históricos.
 */
class HistoricalEventRepository {
    fun consultarEventosHistoricos(callback: (List<EventoHistorico>?) -> Unit) {
        val parametros = HashMap<String, Any>()

        // Llama a la Cloud Function
        NetworkModuleDI.callCloudFunction<HashMap<String, Any>>("hello", parametros) { resultado, e ->
            if (e == null) {
                try {

                    // Extraer el campo "data" y convertirlo a JSON
                    val gson = Gson()
                    val resultadoMap = resultado?.get("data") ?: emptyList<Map<String, Any>>()
                    val json = gson.toJson(resultadoMap)

                    // Log para ver el contenido del JSON
                    val listaTipo = object : TypeToken<List<Map<String, Any>>>() {}.type

                    // Parsear el JSON
                    val evenntosHistoricos = gson.fromJson<List<Map<String, Any>>>(json, listaTipo)

                    // Convertir los datos del JSON a la clase HistoricalEvent
                    val eventos = evenntosHistoricos.map {
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
                    callback(eventos)

                } catch (ex: Exception) {
                    Log.e("HistoricalEventRepo", "Error al procesar el JSON: ${ex.message}")
                    callback(null)
                }
            } else {
                // Manejo de errores
                when {
                    e is ParseException && e.code == ParseException.CONNECTION_FAILED -> {
                        Log.e("HistoricalEventRepo", "Error de conexión: ${e.message}")
                    }
                    e is ParseException && e.code == ParseException.OBJECT_NOT_FOUND -> {
                        Log.e("HistoricalEventRepo", "Datos no encontrados: ${e.message}")
                    }
                    else -> {
                        Log.e("HistoricalEventRepo", "Error desconocido: ${e.message}")
                    }
                }
                callback(null)
            }
        }
    }
}
