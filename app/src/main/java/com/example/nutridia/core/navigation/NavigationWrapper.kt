package com.example.nutridia.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutridia.LoginScreen
import com.example.nutridia.MinutaScreen
import com.example.nutridia.RecetaRepository
import com.example.nutridia.RecuperarScreen
import com.example.nutridia.RegistroScreen
import com.example.nutridia.UsuarioRepository

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val recetas = RecetaRepository.recetas

    // Define navegacion entre pantallas de la aplicacion utilizando Navigation Compose
    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ) {

        composable<Login> {
            LoginScreen(
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
                onRegistrar = { nombre, contrasena, nivelCocina ->
                    UsuarioRepository.registrar(nombre, contrasena, nivelCocina)
                    navController.popBackStack()
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable<Recuperar> {
            RecuperarScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable<Minuta> {
            MinutaScreen(
                recetas = recetas,
                onCerrarSesion = {
                    navController.navigate(Login) {
                        popUpTo(Minuta) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}