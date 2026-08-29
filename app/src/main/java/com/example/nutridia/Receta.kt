package com.example.nutridia

data class Receta(
    val dia: String,
    val nombre: String,
    val ingredientes: List<String>,
    val preparacion: String,
    val recomendacion: String
)