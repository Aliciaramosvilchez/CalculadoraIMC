package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@Suppress("DEPRECATION")
class DetalleIMCActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val calculo = intent.getSerializableExtra("calculo") as? CalculoIMC

        setContent {
            CalculadoraIMCTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    calculo?.let {
                        DetalleIMCPantalla(calculo)
                    } ?: Text("No se recibieron datos.")
                }
            }
        }
    }
}

@Composable
fun DetalleIMCPantalla(calculo: CalculoIMC) {
    val recomendacion = when (calculo.categoria) {
        "Bajo peso" -> "Aumenta tu consumo calórico y consulta con un nutricionista."
        "Peso normal" -> "Sigue manteniendo un estilo de vida saludable."
        "Sobrepeso" -> "Haz ejercicio regularmente y cuida tu alimentación."
        "Obesidad" -> "Consulta con un médico para planificar una pérdida de peso saludable."
        else -> "Sin recomendaciones disponibles."
    }

    val colorCategoria = when (calculo.categoria) {
        "Bajo peso" -> Color.Blue
        "Peso normal" -> Color.Green
        "Sobrepeso" -> Color.Yellow
        "Obesidad" -> Color.Red
        else -> Color.Gray
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Detalles del IMC", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Peso: ${calculo.peso} kg")
        Text("Altura: ${calculo.altura} m")
        Text("IMC: ${calculo.imc}")

        Spacer(modifier = Modifier.height(10.dp))

        Text("Categoría: ${calculo.categoria}", color = colorCategoria)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Recomendación:", style = MaterialTheme.typography.titleMedium)
        Text(recomendacion)
    }
}
