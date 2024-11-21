package com.example.kotlin.examen.data.repositories

import android.util.Log
import com.example.kotlin.examen.data.network.model.Postura
import com.example.kotlin.wushuapp.data.network.NetworkModuleDI
import com.google.gson.Gson
import com.parse.ParseException

/**
 * Repositorio que maneja la lógica de acceso a datos para las posturas.
 */
class PosturaRepository {

    /**
     * Consulta la lista de posturas desde la nube y la devuelve a través de un callback.
     *
     * @param callback Función lambda que recibe una lista de posturas o `null` en caso de error.
     */
    fun consultarPosturas(callback: (List<Postura>?) -> Unit) {
        val parametros = HashMap<String, Any>()

        NetworkModuleDI.callCloudFunction<List<Map<String, String>>>("consultarPosturas", parametros) { resultados, e ->
            if (e == null) {
                val gson = Gson()
                val posturas = resultados?.map { posturaMap ->
                    gson.fromJson(gson.toJson(posturaMap), Postura::class.java)
                }
                callback(posturas)
            } else {
                if (e is ParseException && e.code == 1) {
                    Log.e("PosturaRepository", "Error de conexión con la base de datos: ${e.message}")
                } else if (e is ParseException && e.code == 100) {
                    Log.e("PosturaRepository", "Error de conexión: ${e.message}")
                } else {
                    Log.e("PosturaRepository", "Error: ${e.message}")
                }
                callback(null)
            }
        }
    }
}
