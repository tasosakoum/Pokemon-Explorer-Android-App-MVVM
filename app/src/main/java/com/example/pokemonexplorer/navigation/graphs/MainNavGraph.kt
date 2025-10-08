package com.example.pokemonexplorer.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.domain.models.navigation.MainGraph

fun NavGraphBuilder.mainNavGraph() {
    navigation<MainGraph>(startDestination = MainGraph.startDestination) {
        composable<MainGraph.MainScreen> {
            val args = it.toRoute<MainGraph.MainScreen>()
        }
    }
}