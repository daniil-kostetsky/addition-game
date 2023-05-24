package com.example.calculate.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.calculate.R
import com.example.calculate.domain.entities.GameResult

interface OnAnswerOptionCLickListener {
    fun onAnswerOptionClick(answerOption: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, countOfRequiredAnswers: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.tv_min_count_of_right_answers),
        countOfRequiredAnswers
    )
}

@BindingAdapter("userScore")
fun bindUserScore(textView: TextView, countOfUserScore: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.tv_user_score),
        countOfUserScore
    )
}

@BindingAdapter("minPercentage")
fun bindMinPercentage(textView: TextView, minPercent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.tv_min_percentage),
        minPercent
    )
}

@BindingAdapter("userPercentage")
fun bindUserPercentage(textView: TextView, gameResult: GameResult) {
   val userPercentage = ((gameResult.countOfRightAnswers /
           gameResult.countOfQuestions.toDouble()) * 100).toInt()
    textView.text = String.format(
        textView.context.getString(R.string.tv_user_percentage),
        userPercentage
    )
}

@BindingAdapter("resultSmile")
fun bindResultSmile(imageView: ImageView, isWin: Boolean) {
    if (isWin) {
       imageView.setImageResource(R.drawable.smile_fun)
    } else {
        imageView.setImageResource(R.drawable.smile_sad)
    }
}

@BindingAdapter("setProgressInBar")
fun bindSetProgressBar(progressBar: ProgressBar, percentOfRightAnswers: Int){
    progressBar.setProgress(percentOfRightAnswers, true)
}

@BindingAdapter("tvAnswersProgressTextColor")
fun bindTvAnswersProgressTextColor(textView: TextView, enoughCountOfRightAnswers: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enoughCountOfRightAnswers))
}

@BindingAdapter("progressBarTintList")
fun bindProgressBarTintList(progressBar: ProgressBar, enoughPercentOfRightAnswers: Boolean) {
    val color = getColorByState(progressBar.context, enoughPercentOfRightAnswers)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onAnswerOptionCLickListener")
fun bindOnAnswerOptionClickListener(textView: TextView, clickListener: OnAnswerOptionCLickListener) {
    textView.setOnClickListener {
        clickListener.onAnswerOptionClick(textView.text.toString().toInt())
    }
}

private fun getColorByState(context: Context, state: Boolean): Int {
    val colorResId =
        if (state) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
    return ContextCompat.getColor(context, colorResId)
}