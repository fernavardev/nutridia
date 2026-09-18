package com.example.nutridia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(
    recetas: List<Receta>,
    onVerReceta: (String) -> Unit,
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuDiasExpandido by remember { mutableStateOf(false) }
    var diaSeleccionado by remember { mutableStateOf("") }

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

        val recetaDelDia = RecetaRepository.buscarPorDia(diaSeleccionado)

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
                            onVerReceta(recetaDelDia.dia)
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
}
