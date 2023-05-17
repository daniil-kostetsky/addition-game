package com.example.calculate.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculate.domain.entities.GameLevel

class GameViewModelFactory(
    private val gameLevel: GameLevel,
    private val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(gameLevel, application) as T
        }
        throw IllegalArgumentException("Unknown view model class $modelClass")
    }
}