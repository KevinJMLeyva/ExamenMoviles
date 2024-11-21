package com.example.kotlin.examen.framework.views.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin.examen.R
import com.example.kotlin.examen.databinding.ActivityPosturasBinding
import com.example.kotlin.examen.framework.adapter.PosturaAdapter
import com.example.kotlin.examen.framework.viewmodel.ConsultarPosturaViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Activity que muestra una lista de posturas en un RecyclerView con la capacidad de realizar búsquedas.
 * Utiliza un ViewModel para gestionar los datos y un adaptador para mostrar los elementos.
 */
class PosturasActivity : AppCompatActivity() {
    // Enlace de vista generado para el diseño de la actividad (ActivityPosturasBinding)
    private lateinit var binding: ActivityPosturasBinding

    // ViewModel para manejar los datos y la lógica de negocio de las posturas
    private val viewModel: ConsultarPosturaViewModel by viewModels()

    // Adaptador para el RecyclerView que muestra las posturas
    private lateinit var posturaAdapter: PosturaAdapter

    /**
     * Método que se llama cuando se crea la actividad. Configura el enlace de vista,
     * inicializa el RecyclerView, activa el buscador y observa los datos del ViewModel.
     *
     * @param savedInstanceState Estado de la actividad guardado anteriormente.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inicializarVinculo()
        inicializarRecyclerView()
        eschucharBuscador()
        inicializarListeners()

        viewModel.consultarPosturas()
        observarViewModel()

    }

    /**
     * Inicializa el enlace de vista inflando el diseño y lo establece como el contenido de la actividad.
     */
    private fun inicializarVinculo() {
        binding = ActivityPosturasBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Configura el RecyclerView con un adaptador y un GridLayoutManager de tres columnas.
     * Permite mostrar las posturas en un diseño de cuadrícula.
     */
    private fun inicializarRecyclerView() {
        posturaAdapter = PosturaAdapter()
        binding.recyclerViewPosturas.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewPosturas.adapter = posturaAdapter
    }

    /**
     * Observa los datos del ViewModel. Cuando cambian, actualiza los datos del adaptador
     * y verifica si hay resultados para mostrar u ocultar el estado "Sin Resultados".
     */
    private fun observarViewModel() {
        viewModel.posturasLiveData.observe(this) { posturas ->
            posturaAdapter.establecerPosturaData(posturas, this)
            verificarEstadoResultados(posturas.isEmpty())
        }
    }

    /**
     * Inicializa los listeners para la actividad, incluyendo el botón de retroceso.
     * Al presionar el botón, se cierra la actividad actual.
     */
    private fun inicializarListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Escucha el campo de búsqueda y filtra las posturas en tiempo real a medida
     * que el usuario escribe. Muestra u oculta el mensaje "Sin Resultados" si es necesario.
     */
    private fun eschucharBuscador() {
        binding.buscadorPostura.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    posturaAdapter.filtro(s.toString())
                    verificarEstadoResultados(posturaAdapter.itemCount == 0)
                }

                override fun afterTextChanged(s: Editable?) {}
            },
        )
    }
    /**
     * Verifica si se deben mostrar u ocultar los elementos de "Sin Resultados" según el estado de la lista.
     *
     * @param sinResultados Indica si no se encontraron posturas en la lista.
     */
    private fun verificarEstadoResultados(sinResultados: Boolean) {
        binding.contenedorSinResultados.visibility = if (sinResultados) View.VISIBLE else View.GONE
        binding.recyclerViewPosturas.visibility = if (sinResultados) View.GONE else View.VISIBLE
    }

}
