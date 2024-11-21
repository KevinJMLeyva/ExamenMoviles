package com.example.kotlin.examen.data.network.model

/**
 * Modelo de datos que representa un evento histórico.
 */
data class EventoHistorico(
    val date: String,           // Fecha del evento
    val description: String,    // Descripción del evento
    val lang: String,           // Idioma de la descripción
    val category1: String,      // Categoría principal
    val category2: String,      // Categoría secundaria
    val granularity: String,    // Granularidad temporal
    val createdAt: String,      // Fecha de creación
    val updatedAt: String,      // Fecha de última actualización
    val objectId: String
)

