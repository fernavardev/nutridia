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

        val id = requireArguments().getString("id").orEmpty()

        return ComposeView(requireContext()).apply {
            setContent {
                val receta = RecetaRepository.buscarPorId(id)

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