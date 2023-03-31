package com.williamzabot.composenavkoin.presentation.navigation

sealed class NavigationScreen(val route: String) {
    object TaskOneScreen: NavigationScreen("one")
    object TaskTwoScreen: NavigationScreen("two/{argTest}")
}
