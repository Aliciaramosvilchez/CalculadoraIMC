package com.example.calculadoraimc

import java.io.Serializable

data class CalculoIMC(
    val peso: String,
    val altura: String,
    val imc: String,
    val categoria: String
) : Serializable
