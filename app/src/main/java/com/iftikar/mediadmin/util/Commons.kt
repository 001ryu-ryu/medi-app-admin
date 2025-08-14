package com.iftikar.mediadmin.util

fun initialsFrom(fullName: String): String {
    val parts = fullName.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
    return when {
        parts.size >= 2 -> (parts[0].first().toString() + parts[1].first().toString()).uppercase()
        parts.size == 1 -> parts[0].take(2).uppercase()
        else -> ""
    }
}