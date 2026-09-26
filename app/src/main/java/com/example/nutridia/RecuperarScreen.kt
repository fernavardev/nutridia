package com.example.nutridia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecuperarScreen(
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    // guarda datos ingresados y mensaje generado durante la recuperacion
    var usuario by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recuperar contraseña",
            fontSize = 28.sp
        )

        Text(
            text = "Ingresa tu correo para recuperar tu contraseña",
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                when {
                    usuario.isBlank() -> {
                        mensaje = "Ingrese su correo"
                    }

                    !usuario.esCorreoValido() -> {
                        mensaje = "Ingrese un correo válido"
                    }

                    else -> {
                        scope.launch {
                            val usuarioEncontrado =
                                UsuarioRepository.buscarUsuario(usuario)

                            if (usuarioEncontrado != null) {
                                val enviado =
                                    UsuarioRepository.recuperarContrasena(usuario)

                                mensaje = if (enviado) {
                                    "Correo de recuperación enviado"
                                } else {
                                    "No fue posible enviar el correo de recuperación"
                                }
                            } else {
                                mensaje = "Usuario no encontrado"
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Enviar recuperacion")
        }

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(
            onClick = onVolver,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}