package com.williamzabot.composenavkoin.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.williamzabot.composenavkoin.data.model.Task
import com.williamzabot.composenavkoin.presentation.tasks.TaskScreen
import com.williamzabot.composenavkoin.presentation.tasks.TaskTwoScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.TaskOneScreen.route
    ) {
        composable(route = NavigationScreen.TaskOneScreen.route) {
            TaskScreen {
                val json = Uri.encode(Gson().toJson(it))
                navController.navigate("two/$json")
            }

        }

        composable(route = NavigationScreen.TaskTwoScreen.route,
            arguments = listOf(
                navArgument("argTest") {
                    type = TaskNavType()
                }
            )) {
            val argTest = it.arguments?.getParcelable<Task>("argTest")
            argTest?.let { task ->
                TaskTwoScreen(task = task)
            }
        }
    }

}