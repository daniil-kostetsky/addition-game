package com.example.calculate.domain.repository

import com.example.calculate.domain.entities.GameLevel
import com.example.calculate.domain.entities.GameQuestion
import com.example.calculate.domain.entities.GameSettings

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        numberOfAnswerOptions: Int
    ): GameQuestion

    fun getGameSettings(gameLevel: GameLevel) : GameSettings
}