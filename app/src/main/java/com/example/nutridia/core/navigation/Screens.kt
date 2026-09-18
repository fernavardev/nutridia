package com.example.nutridia.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Registro

@Serializable
object Recuperar

@Serializable
object Home

@Serializable
object Minuta

@Serializable
data class RecetaRoute(
    val dia: String
)