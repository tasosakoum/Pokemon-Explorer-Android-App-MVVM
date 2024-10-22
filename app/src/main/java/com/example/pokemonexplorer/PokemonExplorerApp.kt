package com.example.pokemonexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokemonexplorer.navigation.MainNavHost

@Composable
fun PokemonExplorerApp(
    navHostController: NavHostController = rememberNavController(),
) {
    MainNavHost(navHostController = navHostController) 
}