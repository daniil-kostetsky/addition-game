package com.example.calculate.data

import com.example.calculate.domain.entities.GameLevel
import com.example.calculate.domain.entities.GameQuestion
import com.example.calculate.domain.entities.GameSettings
import com.example.calculate.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, numberOfAnswerOptions: Int): GameQuestion {
        val  sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val answerOptions = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        answerOptions.add(rightAnswer)
        val from = max(rightAnswer - numberOfAnswerOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + numberOfAnswerOptions)
        while (answerOptions.size < numberOfAnswerOptions) {
            answerOptions.add(Random.nextInt(from, to))
        }
        return GameQuestion(sum, visibleNumber, answerOptions.toList())
    }

    override fun getGameSettings(gameLevel: GameLevel): GameSettings {
        return when(gameLevel) {
            GameLevel.TEST -> {
                GameSettings(
                    10,
                    3,
                    50,
                    10
                )
            }
            GameLevel.EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }
            GameLevel.MEDIUM -> {
                GameSettings(
                    50,
                    20,
                    80,
                    45
                )
            }
            GameLevel.HARD -> {
                GameSettings(
                    100,
                    25,
                    90,
                    45
                )
            }
        }
    }
}