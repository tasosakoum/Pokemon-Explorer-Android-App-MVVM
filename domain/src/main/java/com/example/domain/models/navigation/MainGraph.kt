package com.example.domain.models.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainGraph : Graph {
    override val route = "main_graph_route"
    override val startDestination = MainScreen()

    @Serializable
    data class MainScreen(val sup: String = "") : Screen
}