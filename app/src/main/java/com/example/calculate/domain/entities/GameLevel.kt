package com.example.calculate.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameLevel : Parcelable{
    TEST,
    EASY,
    MEDIUM,
    HARD
}