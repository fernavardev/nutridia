package com.example.nutridia

import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun HomeScreen(
    onIrAMinuta: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a NutriDia",
            fontSize = 28.sp
        )

        Text(
            text = "¿Cocinamos algo?",
            fontSize = 20.sp,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 24.dp
            )
        )

        // componentes tradicionales de Android dentro de la vista
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->

                // ViewGroup, contiene el boton tradicional de acceso a la minuta
                LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER

                    val botonMinuta = Button(context).apply {
                        text = "🥪 Ir al menú semanal ⭐"

                        // evento de boton para navegar de Home a la minuta semanal
                        setOnClickListener {
                            onIrAMinuta()
                        }

                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }

                    addView(botonMinuta)
                }
            }
        )
    }
}
