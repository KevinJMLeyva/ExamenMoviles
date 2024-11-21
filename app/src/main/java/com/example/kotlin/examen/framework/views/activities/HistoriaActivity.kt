package com.example.kotlin.examen.framework.views.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin.examen.databinding.ActivityHistoriaBinding
import com.example.kotlin.examen.framework.adapter.HistoriaAdapter
import com.example.kotlin.examen.framework.viewmodel.ConsultarHistoriaViewModel
import com.google.android.material.snackbar.Snackbar

class HistoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoriaBinding
    private val viewModel: ConsultarHistoriaViewModel by viewModels()
    private lateinit var historiaAdapater: HistoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inicializarVinculo()
        inicializarRecyclerView()
        eschucharBuscador()
        inicializarListeners()

        viewModel.consultarDatosHistoricos()
        observarViewModel()
    }

    private fun inicializarVinculo() {
        binding = ActivityHistoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun inicializarListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun inicializarRecyclerView() {
        historiaAdapater = HistoriaAdapter()
        binding.recyclerViewHistoria.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerViewHistoria.adapter = historiaAdapater
    }

    private fun observarViewModel() {
        viewModel.eventosLiveData.observe(this) { eventos ->
            historiaAdapater.establecerPosturaData(eventos, this)
            verificarEstadoResultados(eventos.isEmpty())
        }

        // Observar errores y mostrar Snackbar
        viewModel.errorLiveData.observe(this) { errorMessage ->
            mostrarSnackbar(errorMessage)
        }
    }

    private fun eschucharBuscador() {
        binding.buscadorHistoria.addTextChangedListener(
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
                    historiaAdapater.filtro(s.toString())
                    verificarEstadoResultados(historiaAdapater.itemCount == 0)
                }

                override fun afterTextChanged(s: Editable?) {}
            },
        )
    }

    private fun verificarEstadoResultados(sinResultados: Boolean) {
        binding.contenedorSinResultados.visibility = if (sinResultados) View.VISIBLE else View.GONE
        binding.recyclerViewHistoria.visibility = if (sinResultados) View.GONE else View.VISIBLE
    }

    private fun mostrarSnackbar(mensaje: String) {
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show()
    }
}
