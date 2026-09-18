package com.example.nutridia

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecetaScreen(
    receta: Receta,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ingredientesMarcados = remember {
        mutableStateMapOf<String, Boolean>()
    }

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

        // recorre la coleccion de ingredientes y controla el estado de cada elemento
        for (ingrediente in receta.ingredientes) {
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
            onClick = onVolver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            Text("Volver a la minuta")
        }
    }
}

// componente reutilizable que recibe textos como parametros para construir una fila de una tabla
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