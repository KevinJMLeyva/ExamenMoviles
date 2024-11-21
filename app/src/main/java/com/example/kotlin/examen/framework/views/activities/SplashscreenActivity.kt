package com.example.kotlin.examen.framework.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.kotlin.examen.databinding.ActivitySplashscreenBinding
import com.example.kotlin.examen.framework.viewmodel.SplashscreenViewModel

/**
 * SplashscreenActivity displays a splash screen when the app launches.
 * It observes the loading process and transitions to the login screen once loading is complete.
 */
class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    // ViewModel instance for managing splash screen logic
    private val viewModel: SplashscreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        viewModel.onCreate()
        initializeObservers()

    }

    /**
     * Initializes the binding for the activity and sets the content view.
     */
    private fun initializeBinding() {
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Sets up observers for the ViewModel's LiveData properties.
     * Observes terminarCarga to determine when to transition to the login screen.
     */
    private fun initializeObservers() {
        viewModel.terminarCarga.observe(
            this,
            Observer { terminarCarga ->
                if (terminarCarga) {
                    passViewGoToMain()
                }
            }
        )
    }
    private fun passViewGoToMain() {
        val intent: Intent = Intent(this, HistoriaActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

}

