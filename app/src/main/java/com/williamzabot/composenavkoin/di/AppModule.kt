package com.williamzabot.composenavkoin.di

import com.williamzabot.composenavkoin.data.repositories.TaskRepository
import com.williamzabot.composenavkoin.data.repositories.TaskRepositoryImpl
import com.williamzabot.composenavkoin.presentation.tasks.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TaskRepository> { TaskRepositoryImpl() }
    viewModel { TaskViewModel(taskRepository = get()) }
}