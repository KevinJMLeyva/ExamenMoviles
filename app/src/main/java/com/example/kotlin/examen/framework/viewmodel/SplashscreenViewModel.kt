package com.example.kotlin.examen.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.wushuapp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for the splash screen. Handles the logic for the loading
 * process and signals when the splash screen should finish.
 */
class SplashscreenViewModel : ViewModel() {

    // LiveData to observe when the splash screen loading is complete
    val terminarCarga = MutableLiveData<Boolean>()

    /**
     * Initializes the splash screen logic. Sets terminarCarga to false initially
     * and starts a coroutine that delays for a specified duration before setting
     * terminarCarga to true, signaling that loading is complete.
     */
    fun onCreate() {
        terminarCarga.postValue(false)
        viewModelScope.launch {
            delay(Constants.SPLASHSCREEN_DURATION)
            terminarCarga.postValue(true)
        }
    }
}
