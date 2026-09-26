package com.example.nutridia

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

// Repository que centraliza los usuarios y operaciones asociadas a un usuario
class UsuarioRepository {

    companion object {

        private val auth = FirebaseAuth.getInstance()

        private val database = FirebaseDatabase
            .getInstance()
            .getReference("usuarios")

        // CREATE: registra al usuario en Firebase Authentication
        // y guarda su perfil en Realtime Database
        suspend fun registrar(
            correo: String,
            contrasena: String,
            nivelCocina: String
        ): Boolean {
            return try {
                val resultado = auth
                    .createUserWithEmailAndPassword(correo, contrasena)
                    .await()

                val uid = resultado.user?.uid ?: return false

                val usuario = Usuario(
                    correo = correo,
                    nivelCocina = nivelCocina
                )

                database
                    .child(uid)
                    .setValue(usuario)
                    .await()

                auth.signOut()

                true
            } catch (e: Exception) {
                false
            }
        }

        // READ: autentica las credenciales del usuario
        suspend fun autenticar(
            correo: String,
            contrasena: String
        ): Usuario? {
            return try {
                val resultado = auth
                    .signInWithEmailAndPassword(correo, contrasena)
                    .await()

                val uid = resultado.user?.uid ?: return null

                database
                    .child(uid)
                    .get()
                    .await()
                    .getValue(Usuario::class.java)

            } catch (e: Exception) {
                null
            }
        }

        // Busca un usuario registrado por correo en Realtime Database
        suspend fun buscarUsuario(correo: String): Usuario? {
            return try {
                val snapshot = database.get().await()

                snapshot.children
                    .mapNotNull { it.getValue(Usuario::class.java) }
                    .find { it.correo == correo }

            } catch (e: Exception) {
                null
            }
        }

        // Solicita a Firebase Authentication el envio del correo de recuperacion
        suspend fun recuperarContrasena(correo: String): Boolean {
            return try {
                auth
                    .sendPasswordResetEmail(correo)
                    .await()

                true
            } catch (e: Exception) {
                false
            }
        }

        // UPDATE: actualiza la contraseña del usuario autenticado
        suspend fun actualizarContrasena(nuevaContrasena: String): Boolean {
            return try {
                val usuarioActual = auth.currentUser ?: return false

                usuarioActual
                    .updatePassword(nuevaContrasena)
                    .await()

                true
            } catch (e: Exception) {
                false
            }
        }

        // Cierra la sesion actual de Firebase Authentication
        fun cerrarSesion() {
            auth.signOut()
        }

        // DELETE: elimina los datos y la cuenta del usuario autenticado
        suspend fun eliminarCuenta(): Boolean {
            return try {
                val usuarioActual = auth.currentUser ?: return false
                val uid = usuarioActual.uid

                database
                    .child(uid)
                    .removeValue()
                    .await()

                usuarioActual
                    .delete()
                    .await()

                true
            } catch (e: Exception) {
                false
            }
        }
    }
}