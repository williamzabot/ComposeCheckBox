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
        WeekDay("Segunda"),
        WeekDay("terça"),
        WeekDay("quarta"),
        WeekDay("quinta"),
        WeekDay("sexta"),
        WeekDay("sábado"),
        WeekDay("domingo")
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
}

sealed class UiState {
    object ScreenForm: UiState()
    data class ScreenDetail(val task: Task): UiState()
    object PrintScreen: UiState()
}
