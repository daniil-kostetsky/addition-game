package com.example.calculate.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calculate.R
import com.example.calculate.data.GameRepositoryImpl
import com.example.calculate.domain.entities.GameLevel
import com.example.calculate.domain.entities.GameQuestion
import com.example.calculate.domain.entities.GameResult
import com.example.calculate.domain.entities.GameSettings
import com.example.calculate.domain.usecases.GenerateQuestionUseCase
import com.example.calculate.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

    private var timer: CountDownTimer? = null

    private lateinit var gameSettings: GameSettings
    private lateinit var gameLevel: GameLevel

    private val repository = GameRepositoryImpl
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _gameQuestion = MutableLiveData<GameQuestion>()
    val gameQuestion: LiveData<GameQuestion>
        get() = _gameQuestion

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(gameLevel: GameLevel) {
        getGameSettings(gameLevel)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings(gameLevel: GameLevel) {
        this.gameLevel = gameLevel
        gameSettings = getGameSettingsUseCase(gameLevel)
        _minPercent.value = gameSettings.minPercentOfRightAnswer
    }

    fun chooseAnswer(selectedOption: Int) {
        checkAnswer(selectedOption)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(selectedOption: Int) {
        val rightAnswer = gameQuestion.value?.rightAnswer
        if (rightAnswer == selectedOption) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val userPercent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = userPercent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.answers_progress),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = userPercent >= gameSettings.minPercentOfRightAnswer
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun generateQuestion() {
        _gameQuestion.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND,
            MILLIS_IN_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val tempSeconds = millisUntilFinished / MILLIS_IN_SECOND
        val minutes = tempSeconds / SECONDS_IN_MINUTE
        val seconds = tempSeconds % SECONDS_IN_MINUTE
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {

        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }
}