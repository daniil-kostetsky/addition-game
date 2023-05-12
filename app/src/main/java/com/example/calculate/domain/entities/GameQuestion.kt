package com.example.calculate.domain.entities

data class GameQuestion(
    val sum: Int,
    val visibleNumber: Int,
    val answerOptions: List<Int>
) {
    val rightAnswer: Int
        get() = sum - visibleNumber
}