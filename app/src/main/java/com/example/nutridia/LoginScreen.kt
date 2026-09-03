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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onCrearCuenta: () -> Unit,
    onRecuperarContrasena: () -> Unit,
    onIngresar: () -> Unit,
    modifier: Modifier = Modifier
) {

    // Mantiene estado de los datos ingresados y mensajes de validacion del formulario
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NutriDía 🥜",
            fontSize = 32.sp
        )

        Text(
            text = "Tu menú semanal, fácil y saludable",
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Button(
            onClick = {
                // verifica las credenciales por medio del repositorio de usuarios
                val usuarioValido = UsuarioRepository.autenticar(usuario, contrasena)

                mensajeError = if (usuarioValido) {
                    ""
                } else {
                    "Usuario o contraseña incorrectos"
                }

                if (usuarioValido) {
                    onIngresar()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Ingresar")
        }

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(
            onClick = onCrearCuenta,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Crear cuenta")
        }

        TextButton(
            onClick = onRecuperarContrasena
        ) {
            Text("Recuperar contraseña")
        }
    }
}