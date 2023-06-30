package com.williamzabot.composenavkoin.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamzabot.composenavkoin.data.model.Task
import com.williamzabot.composenavkoin.data.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val listWeekDays = listOf(
        WeekDay("1"),
        WeekDay("2"),
        WeekDay("3"),
        WeekDay("4"),
        WeekDay("5"),
        WeekDay("6"),
        WeekDay("7"),
        WeekDay("8"),
        WeekDay("9"),
        WeekDay("10"),
        WeekDay("11"),
        WeekDay("12"),
        WeekDay("13"),
        WeekDay("14"),
        WeekDay("15"),
        WeekDay("16"),
        WeekDay("17"),
        WeekDay("18"),
        WeekDay("19"),
        WeekDay("20"),
        WeekDay("21")
    )

    private val _weekDays = MutableStateFlow(listWeekDays)
    val weekDays = _weekDays.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.ScreenForm)
    val uiState = _uiState.asStateFlow()

    fun showTaskForm() {
        viewModelScope.launch {
            _uiState.emit(UiState.ScreenForm)
        }
    }

    fun showTaskDetail(task: Task) {
        viewModelScope.launch {
            _uiState.emit(UiState.ScreenDetail(task))
        }
    }

    fun addTask(task: Task) {
        taskRepository.addTask(task)
        viewModelScope.launch {
            _weekDays.emit(_weekDays.value.map { it.copy(checked = false) })
        }
    }

    fun printScreen() {
        viewModelScope.launch {
            _uiState.emit(UiState.PrintScreen)
        }
    }

    fun changeItem(changeDay: WeekDay, changeChecked: Boolean) {
        viewModelScope.launch {
            _weekDays.emit(_weekDays.value.map {
                if (it == changeDay) {
                    it.copy(checked = changeChecked)
                } else {
                    it.copy()
                }
            })
        }
    }

    fun scroll() {
        viewModelScope.launch {
            _uiState.emit(UiState.Scroll)
        }
    }
}

sealed class UiState {
    object ScreenForm : UiState()
    object Scroll : UiState()
    data class ScreenDetail(val task: Task) : UiState()
    object PrintScreen : UiState()
}
