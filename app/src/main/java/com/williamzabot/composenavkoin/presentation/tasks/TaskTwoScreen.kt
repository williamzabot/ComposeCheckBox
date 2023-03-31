package com.williamzabot.composenavkoin.presentation.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.williamzabot.composenavkoin.data.model.Task


@Composable
fun TaskTwoScreen(task: Task) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp)) {
        Text(text = "Tela 2", modifier = Modifier.align(CenterHorizontally))
        Text(text = task.description, modifier = Modifier.align(CenterHorizontally))
    }
}