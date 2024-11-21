package com.example.kotlin.examen.domain

import com.example.kotlin.examen.data.network.model.Postura
import com.example.kotlin.examen.data.repositories.PosturaRepository

/**
 * Clase que encapsula el requisito de consultar posturas.
 * Esta clase interactúa con `PosturaRepository` para obtener la lista de posturas.
 */
class ConsultarPosturaRequirement {

    // Instancia del repositorio para realizar consultas de posturas.
    private val posturaRepository = PosturaRepository()

    /**
     * Sobrecarga del operador `invoke` para consultar posturas.
     *
     * @param callback Función lambda que recibe una lista de posturas o `null` en caso de error.
     * La función `invoke` permite utilizar esta clase como una llamada de función, simplificando su uso.
     */
    operator fun invoke(callback: (List<Postura>?) -> Unit) {
        // Llama al método de consulta en el repositorio y pasa el resultado al callback.
        posturaRepository.consultarPosturas { posturas ->
            callback(posturas)
        }
    }
}
