package com.williamzabot.composenavkoin.presentation.tasks

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.williamzabot.composenavkoin.data.model.Task
import com.williamzabot.composenavkoin.presentation.utils.PrintScreen
import com.williamzabot.composenavkoin.presentation.utils.formatListWithCommaAndSpace
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreen(
    context: Context,
    viewModel: TaskViewModel = koinViewModel(),
    onNavigateClick: (task: Task) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
                .weight(1f)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.LightGray)
                    .border(BorderStroke(1.dp, Color.Black))
            ) {
                Text(
                    text = "Header",
                    modifier = Modifier
                        .padding(top = 14.dp, start = 150.dp)
                )
            }
            val weekDays = viewModel.weekDays.collectAsState()
            val uiState = viewModel.uiState.collectAsState()
            when (val data = uiState.value) {
                is UiState.ScreenForm -> {
                    ShowForm(weekDays, viewModel)
                }
                is UiState.ScreenDetail -> {
                    TaskDetail(task = data.task) {
                        viewModel.showTaskForm()
                    }
                }
                is UiState.PrintScreen -> {
                    ShowForm(weekDays, viewModel)
                    PrintScreen(
                        view = LocalView.current,
                        context = context
                    )
                    viewModel.showTaskForm()
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            /*Button(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(all = 10.dp),
                onClick = {
                    onNavigateClick(
                        Task("task enviada", listOf("Sábado"))
                    )
                }) {
                Text(text = "Navegar")
            }*/
            Button(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(all = 10.dp),
                onClick = {
                    viewModel.printScreen()
                }) {
                Text(text = "Print")
            }
        }
    }
}

@Composable
private fun ShowForm(
    weekDays: State<List<WeekDay>>,
    viewModel: TaskViewModel
) {
    TaskForm(
        weekDays.value,
        changedItemCheckBox = { day, checked ->
            viewModel.changeItem(day, checked)
        },
        onSaveClick = { task ->
            viewModel.addTask(task)
            viewModel.showTaskDetail(task)
        })
}

@Composable
private fun TaskForm(
    weekDays: List<WeekDay>,
    changedItemCheckBox: (changedDay: WeekDay, isChecked: Boolean) -> Unit,
    onSaveClick: (task: Task) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 10.dp),
            text = "Inserir Task"
        )
        val inputValue = remember { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 10.dp),
            value = inputValue.value,
            onValueChange = {
                inputValue.value = it
            })
        Column(modifier = Modifier.padding(top = 6.dp)) {
            weekDays.forEach { weekDay ->
                ItemLayout(weekDay = weekDay) { changedDay, changedChecked ->
                    changedItemCheckBox(changedDay, changedChecked)
                }
            }
        }
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .align(CenterHorizontally)
                .background(color = Color.Black),
            onClick = {
                if (inputValue.value.isNotBlank() && weekDays.isNotEmpty()) {
                    val task =
                        Task(inputValue.value, weekDays.filter { it.checked }.map { it.name })
                    onSaveClick(task)
                    /** clear text field */
                    inputValue.value = ""
                } else {
                    Toast.makeText(context, "Campos vazios", Toast.LENGTH_SHORT).show()
                }
            }) {
            Text(text = "Salvar", modifier = Modifier.padding(10.dp), color = Color.White)
        }
    }
}

@Composable
private fun TaskDetail(task: Task, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        Text(text = task.description, modifier = Modifier.padding(top = 20.dp))
        val listDays = mutableListOf("Dias: ")
        listDays.addAll(task.days)
        Row {
            var allDays = ""
            listDays.forEach { day ->
                allDays += day.formatListWithCommaAndSpace(
                    first = listDays.first(),
                    last = listDays.last()
                )
            }
            Text(text = allDays)
        }
        Button(
            onClick = { onBackClick() },
            Modifier.padding(top = 10.dp),
        ) {
            Text(text = "Voltar", color = Color.Black)
        }
    }
}

@Composable
private fun ItemLayout(
    weekDay: WeekDay,
    onChanged: (weekDay: WeekDay, isChecked: Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
        Checkbox(
            checked = weekDay.checked,
            onCheckedChange = {
                onChanged(weekDay, it)
            }
        )
        Text(text = weekDay.name)
    }
}

data class WeekDay(val name: String, var checked: Boolean = false)