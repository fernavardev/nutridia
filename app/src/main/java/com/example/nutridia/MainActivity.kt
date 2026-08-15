package com.example.nutridia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutridia.ui.theme.NutriDiaTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.mutableStateListOf

enum class Pantalla {
    LOGIN,
    REGISTRO,
    RECUPERAR,
    MINUTA
}

data class Usuario(
    val nombre: String,
    var contrasena: String
)

data class Receta(
    val dia: String,
    val nombre: String,
    val descripcion: String,
    val recomendacion: String
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val usuarios = remember {
                mutableStateListOf(
                    Usuario("admin", "1234")
                )
            }

            val recetas = remember {
                listOf(
                    Receta(
                        dia = "Lunes",
                        nombre = "Pollo con arroz y verduras",
                        descripcion = "Pechuga de pollo acompañada de arroz y verduras frescas.",
                        recomendacion = "Una comida equilibrada que combina proteínas, carbohidratos y verduras."
                    ),
                    Receta(
                        dia = "Martes",
                        nombre = "Lentejas con verduras",
                        descripcion = "Lentejas preparadas con zanahoria, cebolla y otras verduras.",
                        recomendacion = "Las lentejas aportan proteínas vegetales, fibra y minerales."
                    ),
                    Receta(
                        dia = "Miércoles",
                        nombre = "Pescado con papas cocidas",
                        descripcion = "Pescado preparado a la plancha acompañado de papas cocidas y ensalada.",
                        recomendacion = "El pescado es una buena fuente de proteínas y grasas saludables."
                    ),
                    Receta(
                        dia = "Jueves",
                        nombre = "Pasta con salsa de tomate",
                        descripcion = "Pasta acompañada de salsa de tomate casera y verduras.",
                        recomendacion = "Complementa la pasta con verduras para obtener una comida más equilibrada."
                    ),
                    Receta(
                        dia = "Viernes",
                        nombre = "Ensalada de pollo",
                        descripcion = "Ensalada fresca con pollo, lechuga, tomate, zanahoria y otros vegetales.",
                        recomendacion = "Una alternativa ligera que combina proteínas con una variedad de verduras."
                    )
                )
            }

            var pantallaActual by remember { mutableStateOf(Pantalla.LOGIN) }

            NutriDiaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (pantallaActual) {

                        Pantalla.LOGIN -> LoginScreen(
                            usuarios = usuarios,
                            onCrearCuenta = {
                                pantallaActual = Pantalla.REGISTRO
                            },
                            onRecuperarContrasena = {
                                pantallaActual = Pantalla.RECUPERAR
                            },
                            onIngresar = {
                                pantallaActual = Pantalla.MINUTA
                            },
                            modifier = Modifier.padding(innerPadding)
                        )

                        Pantalla.REGISTRO -> RegistroScreen(
                            onRegistrar = { nombre, contrasena ->
                                usuarios.add(
                                    Usuario(nombre, contrasena)
                                )
                                pantallaActual = Pantalla.LOGIN
                            },
                            onVolver = {
                                pantallaActual = Pantalla.LOGIN
                            },
                            modifier = Modifier.padding(innerPadding)
                        )

                        Pantalla.RECUPERAR -> RecuperarScreen(
                            usuarios = usuarios,
                            onVolver = {
                                pantallaActual = Pantalla.LOGIN
                            },
                            modifier = Modifier.padding(innerPadding)
                        )

                        Pantalla.MINUTA -> MinutaScreen(
                            recetas = recetas,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    usuarios: List<Usuario>,
    onCrearCuenta: () -> Unit,
    onRecuperarContrasena: () -> Unit,
    onIngresar: () -> Unit,
    modifier: Modifier = Modifier
) {

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
            text = "NutriDía",
            fontSize = 32.sp
        )

        Text(
            text = "Tu menú semanal, fácil y saludable",
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
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
                val usuarioValido = usuarios.any {
                    it.nombre == usuario && it.contrasena == contrasena
                }

                if (usuarioValido) {
                    mensajeError = ""
                    onIngresar()
                } else {
                    mensajeError = "Usuario o contraseña incorrectos"
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

@Composable
fun RegistroScreen(
    onRegistrar: (String, String) -> Unit,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta",
            fontSize = 28.sp
        )

        Text(
            text = "Ingresa tus datos para registrarte",
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
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
                if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                    onRegistrar(usuario, contrasena)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Registrarse")
        }

        TextButton(
            onClick = onVolver,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}

@Composable
fun RecuperarScreen(
    usuarios: List<Usuario>,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var usuario by remember { mutableStateOf("") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

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
            text = "Ingresa tu usuario y una nueva contraseña",
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nuevaContrasena,
            onValueChange = { nuevaContrasena = it },
            label = { Text("Nueva contraseña") },
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
                if (usuario.isBlank() || nuevaContrasena.isBlank()) {
                    mensaje = "Completa todos los campos"
                } else {
                    val usuarioEncontrado = usuarios.find {
                        it.nombre == usuario
                    }

                    if (usuarioEncontrado != null) {
                        usuarioEncontrado.contrasena = nuevaContrasena
                        mensaje = "Contraseña actualizada correctamente"
                    } else {
                        mensaje = "Usuario no encontrado"
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
            Text("Volver al inicio")
        }
    }
}

@Composable
fun MinutaScreen(
    recetas: List<Receta>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Minuta semanal",
            fontSize = 28.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NutriDiaTheme {
        LoginScreen(
            usuarios = listOf(
                Usuario("admin", "1234")
            ),
            onCrearCuenta = {},
            onRecuperarContrasena = {},
            onIngresar = {}
        )
    }
}