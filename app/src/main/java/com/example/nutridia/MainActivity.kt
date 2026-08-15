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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card

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
    val ingredientes: String,
    val preparacion: String,
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
                        ingredientes = "Pechuga de pollo, arroz, zanahoria, pimentón, brócoli y cebolla.",
                        preparacion = "1. Cocina el arroz hasta que esté listo.\n2. Cocina el pollo en una sartén junto con las verduras.\n3. Sirve el pollo y las verduras acompañados del arroz.",
                        recomendacion = "Una comida equilibrada que combina proteínas, carbohidratos y verduras."
                    ),
                    Receta(
                        dia = "Martes",
                        nombre = "Lentejas con verduras",
                        ingredientes = "Lentejas, zanahoria, cebolla, pimentón, zapallo y ajo.",
                        preparacion = "1. Cocina las lentejas en agua hasta que comiencen a ablandarse.\n2. Agrega las verduras picadas y continúa la cocción.\n3. Cocina hasta que las lentejas y verduras estén tiernas y sirve caliente.",
                        recomendacion = "Las lentejas aportan proteínas vegetales, fibra y minerales."
                    ),
                    Receta(
                        dia = "Miércoles",
                        nombre = "Pescado con papas cocidas",
                        ingredientes = "Filete de pescado, papas, lechuga, tomate, limón y condimentos.",
                        preparacion = "1. Cocina las papas en agua hasta que estén blandas.\n2. Cocina el pescado a la plancha y prepara una ensalada de lechuga y tomate.\n3. Sirve el pescado acompañado de las papas y la ensalada.",
                        recomendacion = "El pescado es una buena fuente de proteínas y grasas saludables."
                    ),
                    Receta(
                        dia = "Jueves",
                        nombre = "Pasta con salsa de tomate",
                        ingredientes = "Pasta, tomate, cebolla, zanahoria, ajo y condimentos.",
                        preparacion = "1. Cocina la pasta según las indicaciones hasta que esté lista.\n2. Prepara una salsa cocinando el tomate junto con la cebolla, zanahoria y ajo.\n3. Mezcla la pasta con la salsa y sirve.",
                        recomendacion = "Complementar la pasta con verduras ayuda a obtener una comida más equilibrada."
                    ),
                    Receta(
                        dia = "Viernes",
                        nombre = "Ensalada de pollo",
                        ingredientes = "Pechuga de pollo, lechuga, tomate, zanahoria, pepino y limón.",
                        preparacion = "1. Cocina la pechuga de pollo y córtala en trozos.\n2. Lava y corta las verduras para preparar la ensalada.\n3. Incorpora el pollo, mezcla los ingredientes y aliña con limón.",
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
    var recetaSeleccionada by remember { mutableStateOf<Receta?>(null) }

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

            recetas.forEach { receta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "🗓️️️ ${receta.dia}",
                            fontSize = 20.sp
                        )

                        Text(
                            text = receta.nombre,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Button(
                            onClick = {
                                recetaSeleccionada = receta
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

            Text(
                text = "Ingredientes",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )

            Text(
                text = receta.ingredientes,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

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