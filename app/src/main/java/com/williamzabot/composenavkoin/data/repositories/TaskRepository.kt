package com.williamzabot.composenavkoin.data.repositories

import com.williamzabot.composenavkoin.data.model.Task

interface TaskRepository {
    fun getTasks(): List<Task>?
    fun addTask(task: Task)
}


class TaskRepositoryImpl : TaskRepository {
    val _tasks = mutableListOf<Task>()

    override fun getTasks(): List<Task>? {
        return _tasks
    }

    override fun addTask(task: Task) {
        _tasks.add(task)
    }
}