package com.example.calculate.domain.usecases

import com.example.calculate.domain.entities.GameQuestion
import com.example.calculate.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
){

    operator fun invoke(maxSumValue: Int): GameQuestion {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}