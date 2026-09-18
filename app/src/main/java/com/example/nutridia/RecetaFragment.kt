package com.example.nutridia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class RecetaFragment : Fragment() {

    var onVolver: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dia = requireArguments().getString("dia").orEmpty()

        return ComposeView(requireContext()).apply {
            setContent {
                val receta = RecetaRepository.buscarPorDia(dia)

                if (receta != null) {
                    RecetaScreen(
                        receta = receta,
                        onVolver = {
                            onVolver?.invoke()
                        }
                    )
                }
            }
        }
    }
}