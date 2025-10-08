package com.example.pokemonexplorer.utils.methods

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.domain.models.navigation.Screen
import org.koin.androidx.compose.koinViewModel

fun NavController.pokeNavigateSingleTop(
    screen: Screen,
    alsoClearBackStack: Boolean = false,
    alsoPopUpTo: Screen?,
    inclusivePopUp: Boolean = true,
) {
    if (isSafeToNavigate()) {
        this.navigate(screen) {
            launchSingleTop = true
            if (alsoClearBackStack) popUpTo(0)
            else if (alsoPopUpTo != null) popUpTo(alsoPopUpTo) { inclusive = inclusivePopUp }
        }
    }
}

private fun NavController.isSafeToNavigate(): Boolean =
    this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

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
