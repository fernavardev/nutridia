package com.example.nutridia

data class Receta(
    val id: String = "",
    val dia: String = "",
    val nombre: String = "",
    val ingredientes: List<String> = emptyList(),
    val preparacion: String = "",
    val recomendacion: String = ""
)