package com.example.pokemonexplorer.utils.methods

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.domain.models.navigation.Screen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.screen(
    screen: Screen,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = generateRoute(screen),
        arguments = screen.parameters.map {
            navArgument(it) { }
        }
    ) {
        content(it)
    }
}

private fun generateRoute(screen: Screen): String {
    val builder = StringBuilder(screen.route)
    screen.parameters.forEach {
        builder.append("/{$it}")
    }

    return builder.toString()
}

fun NavController.pokeNavigateSingleTop(
    route: String,
    alsoClearBackStack: Boolean = false,
    alsoPopUpTo: Screen?,
    inclusivePopUp: Boolean = true,
    withParameters: List<String>
) {
    if (isSafeToNavigate()) {
        this.navigate(route.appendParameters(withParameters)) {
            launchSingleTop = true
            if (alsoClearBackStack) popUpTo(0)
            else if (alsoPopUpTo != null) popUpTo(alsoPopUpTo.route) { inclusive = inclusivePopUp }
        }
    }
}

private fun NavController.isSafeToNavigate(): Boolean =
    this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

private fun String.appendParameters(parameters: List<String>): String {
    val builder = StringBuilder(this)
    parameters.forEach {
        builder.append("/$it")
    }

    return builder.toString()
}

fun NavController.pokePopBackStack() {
    if (isSafeToNavigate()) popBackStack()
}

@Composable
inline fun <reified VM: ViewModel> NavController.routeViewModelWithBackStack(
    entry: NavBackStackEntry,
    graphRoute: String? = null
): VM {
    val inRoute = graphRoute ?: entry.destination.route
    val backStackEntry = remember(entry) {
        if (inRoute != null)
            getBackStackEntry(inRoute)
        else
            currentBackStackEntry
    }

    return koinViewModel(viewModelStoreOwner = backStackEntry ?: entry)
}
