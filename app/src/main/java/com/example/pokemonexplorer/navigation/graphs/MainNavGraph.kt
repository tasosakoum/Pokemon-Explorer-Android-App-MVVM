package com.example.pokemonexplorer.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.domain.models.navigation.MainGraph
import com.example.pokemonexplorer.utils.methods.screen

fun NavGraphBuilder.mainNavGraph() {
    navigation(
        route = MainGraph.route,
        startDestination = MainGraph.startDestination.route
    ) {
        screen(MainGraph.MainScreen) {

        }
    }
}