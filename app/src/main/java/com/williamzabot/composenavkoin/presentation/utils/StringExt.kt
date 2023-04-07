package com.williamzabot.composenavkoin.presentation.utils

fun String.formatListWithCommaAndSpace(first: String, last: String): String {
    val char = if (this == first || this == last) {
        ""
    } else {
        ", "
    }
    return "$this$char"
}
