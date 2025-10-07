package com.example.domain.models.navigation

data object MainGraph : Graph {
    override val route = "main_graph_route"
    override val startDestination = MainScreen()

    data class MainScreen(val sup: String = "") : Screen

}