package com.williamzabot.composenavkoin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(val description: String, val days: List<String> = emptyList()): Parcelable