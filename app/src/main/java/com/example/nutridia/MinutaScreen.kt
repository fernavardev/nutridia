package com.example.nutridia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(
    recetas: List<Receta>,
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier
) {
    var recetaSeleccionada by remember { mutableStateOf<Receta?>(null) }

    var menuDiasExpandido by remember { mutableStateOf(false) }
    var diaSeleccionado by remember { mutableStateOf("") }

    val ingredientesMarcados = remember {
        mutableStateMapOf<String, Boolean>()
    }

    if (recetaSeleccionada == null) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Minuta semanal",
                fontSize = 28.sp
            )

            Text(
                text = "¿Qué cocinamos hoy?",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Selecciona un día para conocer tu menú",
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Text(
                text = "Seleccione día",
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = menuDiasExpandido,
                onExpandedChange = {
                    menuDiasExpandido = !menuDiasExpandido
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                OutlinedTextField(
                    value = diaSeleccionado.ifEmpty {
                        "Seleccionar día"
                    },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = menuDiasExpandido
                        )
                    },
                    modifier = Modifier
                        .menuAnchor(
                            ExposedDropdownMenuAnchorType.PrimaryNotEditable
                        )
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = menuDiasExpandido,
                    onDismissRequest = {
                        menuDiasExpandido = false
                    }
                ) {
                    recetas.forEach { receta ->
                        DropdownMenuItem(
                            text = {
                                Text(receta.dia)
                            },
                            onClick = {
                                diaSeleccionado = receta.dia
                                menuDiasExpandido = false
                            }
                        )
                    }
                }
            }

            val recetaDelDia = recetas.find {
                it.dia == diaSeleccionado
            }

            if (recetaDelDia != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "🗓️ ${recetaDelDia.dia}",
                            fontSize = 20.sp
                        )

                        Text(
                            text = recetaDelDia.nombre,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Button(
                            onClick = {
                                recetaSeleccionada = recetaDelDia
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text("Ver menú")
                        }
                    }
                }
            }

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Cerrar sesión")
            }
        }

    } else {

        val receta = recetaSeleccionada!!

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🍽️ ${receta.dia}",
                fontSize = 20.sp
            )

            Text(
                text = receta.nombre,
                fontSize = 28.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información del menú",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    FilaTabla(
                        etiqueta = "Día",
                        valor = receta.dia
                    )

                    FilaTabla(
                        etiqueta = "Menú",
                        valor = receta.nombre
                    )
                }
            }

            Text(
                text = "Ingredientes",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )

            receta.ingredientes.forEach { ingrediente ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = ingredientesMarcados[ingrediente] ?: false,
                        onCheckedChange = { marcado ->
                            ingredientesMarcados[ingrediente] = marcado
                        }
                    )

                    Text(
                        text = ingrediente
                    )
                }
            }

            Text(
                text = "Preparación",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )

            Text(
                text = receta.preparacion,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Text(
                text = "Recomendación nutricional",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )

            Text(
                text = receta.recomendacion,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Button(
                onClick = {
                    recetaSeleccionada = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Text("Volver a la minuta")
            }
        }
    }
}

@Composable
fun FilaTabla(
    etiqueta: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .padding(8.dp)
    ) {
        Text(
            text = "$etiqueta:",
            modifier = Modifier.weight(1f)
        )

        Text(
            text = valor,
            modifier = Modifier.weight(2f)
        )
    }
}