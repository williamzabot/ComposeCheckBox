package com.williamzabot.composenavkoin.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.williamzabot.composenavkoin.data.model.Task

class TaskNavType : NavType<Task>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Task? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Task {
        return Gson().fromJson(value, Task::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Task) {
        bundle.putParcelable(key, value)
    }
}