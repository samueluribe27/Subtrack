package com.example.subtrack.ui.view

import java.io.Serializable

/**
 * Clase de datos que representa una suscripción.
 * Debe ser Serializable para pasarse en un Intent entre actividades.
 */
data class Subscription(
    val id: String,
    val name: String,
    val price: Double,
    val category: String,
    val nextPaymentDate: String // Fecha de cobro como string "dd/MM/yyyy"
) : Serializable