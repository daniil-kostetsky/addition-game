package com.example.calculate.domain.usecases

import com.example.calculate.domain.entities.GameLevel
import com.example.calculate.domain.entities.GameSettings
import com.example.calculate.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(gameLevel: GameLevel): GameSettings {
        return repository.getGameSettings(gameLevel)
    }
}