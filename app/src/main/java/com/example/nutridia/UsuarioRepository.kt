package com.example.nutridia

import androidx.compose.runtime.mutableStateListOf

// repository que centraliza los usuarios y operaciones asociadas a un usuario
class UsuarioRepository {

    companion object {
        // coleccion mutable de usuarios que se utiliza durante la ejecucion
        val usuarios = mutableStateListOf(
            Usuario("admin@correo.cl", "1234", "Bajo")
        )

        fun autenticar(nombre: String, contrasena: String): Boolean {
            return usuarios.any {
                it.nombre == nombre && it.contrasena == contrasena
            }
        }

        fun registrar(nombre: String, contrasena: String, nivelCocina: String) {
            usuarios.add(
                Usuario(nombre, contrasena, nivelCocina)
            )
        }

        fun buscarUsuario(nombre: String): Usuario? {
            return usuarios.find {
                it.nombre == nombre
            }
        }

        fun actualizarContrasena(usuario: Usuario, nuevaContrasena: String) {
            usuario.contrasena = nuevaContrasena
        }
    }
}