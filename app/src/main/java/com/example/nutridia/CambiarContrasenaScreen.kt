package com.example.nutridia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CambiarContrasenaScreen(
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
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
            text = "Cambiar contraseña",
            fontSize = 28.sp
        )

        Text(
            text = "Ingresa tu nueva contraseña",
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = nuevaContrasena,
            onValueChange = {
                if (it.length <= 6 && it.all { caracter -> caracter.isDigit() }) {
                    nuevaContrasena = it
                }
            },
            label = { Text("Nueva contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmarContrasena,
            onValueChange = {
                if (it.length <= 6 && it.all { caracter -> caracter.isDigit() }) {
                    confirmarContrasena = it
                }
            },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Button(
            onClick = {
                when {
                    nuevaContrasena.isBlank() || confirmarContrasena.isBlank() -> {
                        mensaje = "Complete todos los campos"
                    }

                    nuevaContrasena.length != 6 -> {
                        mensaje = "La contraseña debe tener 6 dígitos"
                    }

                    nuevaContrasena != confirmarContrasena -> {
                        mensaje = "Las contraseñas no coinciden"
                    }

                    else -> {
                        scope.launch {
                            val actualizado =
                                UsuarioRepository.actualizarContrasena(nuevaContrasena)

                            mensaje = if (actualizado) {
                                "Contraseña actualizada correctamente"
                            } else {
                                "No fue posible actualizar la contraseña"
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Cambiar contraseña")
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
            Text("Volver")
        }
    }
}