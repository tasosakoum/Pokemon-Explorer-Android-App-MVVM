package com.example.domain.models.navigation

sealed interface Graph {
    val route: String
    val startDestination: Screen
}