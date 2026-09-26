package com.example.nutridia.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutridia.HomeScreen
import com.example.nutridia.LoginScreen
import com.example.nutridia.CambiarContrasenaScreen
import com.example.nutridia.MinutaScreen
import com.example.nutridia.RecetaFragment
import com.example.nutridia.RecetaRepository
import com.example.nutridia.RecuperarScreen
import com.example.nutridia.RegistroScreen
import com.example.nutridia.UsuarioRepository
import androidx.navigation.toRoute
import android.os.Bundle
import androidx.fragment.compose.AndroidFragment
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val scope = rememberCoroutineScope()

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
                onIngresar = { correo, contrasena ->
                    val usuario = UsuarioRepository.autenticar(
                        correo,
                        contrasena
                    )

                    if (usuario != null) {
                        RecetaRepository.cargarDatosIniciales()
                        RecetaRepository.cargarRecetas()

                        navController.navigate(Home) {
                            popUpTo(Login) {
                                inclusive = true
                            }
                        }

                        true
                    } else {
                        false
                    }
                }
            )
        }

        composable<Registro> {
            RegistroScreen(
                onRegistrar = { correo, contrasena, nivelCocina ->
                    val registrado = UsuarioRepository.registrar(
                        correo,
                        contrasena,
                        nivelCocina
                    )

                    if (registrado) {
                        navController.popBackStack()
                    }

                    registrado
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

        composable<Home> {
            HomeScreen(
                onIrAMinuta = {
                    navController.navigate(Minuta)
                },
                onCambiarContrasena = {
                    navController.navigate(CambiarContrasena)
                },
                onCerrarSesion = {
                    UsuarioRepository.cerrarSesion()

                    navController.navigate(Login) {
                        popUpTo(Home) {
                            inclusive = true
                        }
                    }
                },
                onEliminarCuenta = {
                    scope.launch {
                        val eliminado = UsuarioRepository.eliminarCuenta()

                        if (eliminado) {
                            navController.navigate(Login) {
                                popUpTo(Home) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
            )
        }

        composable<CambiarContrasena> {
            CambiarContrasenaScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable<Minuta> {
            MinutaScreen(
                recetas = recetas,
                onVerReceta = { id ->
                    navController.navigate(
                        RecetaRoute(id = id)
                    )
                }
            )
        }

        composable<RecetaRoute> { backStackEntry ->

            val recetaRoute = backStackEntry.toRoute<RecetaRoute>()

            AndroidFragment<RecetaFragment>(
                arguments = Bundle().apply {
                    putString("id", recetaRoute.id)
                },
                onUpdate = { fragment ->
                    fragment.onVolver = {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}