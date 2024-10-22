package com.example.pokemonexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.data.event_bus.getNavControllerEventBusFlow
import com.example.domain.models.navigation.MainGraph
import com.example.domain.models.navigation.NavEvent
import com.example.pokemonexplorer.navigation.graphs.mainNavGraph
import com.example.pokemonexplorer.utils.methods.pokeNavigateSingleTop
import com.example.pokemonexplorer.utils.methods.pokePopBackStack

@Composable
internal fun MainNavHost(
    navHostController: NavHostController,
) {
    val currentScreen by navHostController.currentBackStackEntryAsState()
    val navEvent by getNavControllerEventBusFlow().collectAsState(null)

    LaunchedEffect(Unit) {
        getNavControllerEventBusFlow().collect {
            when (val event = navEvent) {
                is NavEvent.Navigate -> navHostController.pokeNavigateSingleTop(
                    route = event.route,
                    alsoClearBackStack = event.alsoClearBackstack,
                    alsoPopUpTo = event.alsoPopUpTo,
                    inclusivePopUp = event.inclusivePopUp,
                    withParameters = event.withParameters
                )
                is NavEvent.NavigateUp -> navHostController.pokePopBackStack()
                is NavEvent.NavigateUpTo -> navHostController.popBackStack(
                    event.screen.route,
                    false
                )
                else -> {}
            }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = MainGraph.route
    ) {
        mainNavGraph()
    }
}