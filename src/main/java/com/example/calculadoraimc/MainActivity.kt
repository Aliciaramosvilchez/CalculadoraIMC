package com.example.calculadoraimc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraIMCTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    IMCCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun IMCCalculatorScreen() {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var imcResultado by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var colorCategoria by remember { mutableStateOf(Color.Gray) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var botonDetallesHabilitado by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            confirmButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("OK")
                }
            },
            title = { Text("Error") },
            text = { Text("Por favor, ingresa el peso y la altura") }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Calculadora de IMC", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura (m)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                if (peso.isBlank() || altura.isBlank()) {
                    mostrarDialogo = true
                    return@Button
                }

                val pesoVal = peso.toFloatOrNull()
                val alturaVal = altura.toFloatOrNull()

                if (pesoVal != null && alturaVal != null && alturaVal > 0) {
                    val imc = pesoVal / (alturaVal * alturaVal)
                    val imcTexto = String.format("%.1f", imc)
                    imcResultado = imcTexto

                    when {
                        imc < 18.5 -> {
                            categoria = "Bajo peso"
                            colorCategoria = Color.Blue
                        }
                        imc < 25.0 -> {
                            categoria = "Peso normal"
                            colorCategoria = Color.Green
                        }
                        imc < 30.0 -> {
                            categoria = "Sobrepeso"
                            colorCategoria = Color.Yellow
                        }
                        else -> {
                            categoria = "Obesidad"
                            colorCategoria = Color.Red
                        }
                    }
                    botonDetallesHabilitado = true
                }

            }) {
                Text("Calcular IMC")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {
                peso = ""
                altura = ""
                imcResultado = ""
                categoria = ""
                botonDetallesHabilitado = false
            }) {
                Text("Limpiar")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (imcResultado.isNotBlank()) {
            Text("IMC: $imcResultado", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Categoría: $categoria",
                color = colorCategoria,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val intent = Intent(context, DetalleIMCActivity::class.java)
                val calculo = CalculoIMC(peso, altura, imcResultado, categoria)
                intent.putExtra("calculo", calculo)
                context.startActivity(intent)
            },
            enabled = botonDetallesHabilitado
        ) {
            Text("Mostrar Detalles")
        }
    }
}
