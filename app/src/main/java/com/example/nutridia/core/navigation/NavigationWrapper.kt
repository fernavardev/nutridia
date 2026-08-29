package com.example.nutridia.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutridia.LoginScreen
import com.example.nutridia.MinutaScreen
import com.example.nutridia.Receta
import com.example.nutridia.RecuperarScreen
import com.example.nutridia.RegistroScreen
import com.example.nutridia.Usuario

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    // Datos compartidos durante la ejecución de la aplicación.
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

    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ) {

        composable<Login> {
            LoginScreen(
                usuarios = usuarios,
                onCrearCuenta = {
                    navController.navigate(Registro)
                },
                onRecuperarContrasena = {
                    navController.navigate(Recuperar)
                },
                onIngresar = {
                    navController.navigate(Minuta) {
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Registro> {
            RegistroScreen(
                onRegistrar = { nombre, contrasena ->
                    usuarios.add(
                        Usuario(nombre, contrasena)
                    )
                    navController.popBackStack()
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable<Recuperar> {
            RecuperarScreen(
                usuarios = usuarios,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable<Minuta> {
            MinutaScreen(
                recetas = recetas
            )
        }
    }
}