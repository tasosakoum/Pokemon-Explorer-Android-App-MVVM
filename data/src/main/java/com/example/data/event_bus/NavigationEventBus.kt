package com.example.data.event_bus

import com.example.domain.models.navigation.Graph
import com.example.domain.models.navigation.NavEvent
import com.example.domain.models.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

internal class NavigationEventBus {

    private val _navEvent = MutableSharedFlow<NavEvent?>()
    val navEvent = _navEvent.asSharedFlow()

    fun emitEvent(event: NavEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            _navEvent.emit(event)
        }
    }

}

fun navigateTo(
    destination: Screen,
    alsoClearBackStack: Boolean = false,
    alsoPopUpTo: Screen? = null,
    inclusivePopUp: Boolean = true,
    withParameters: List<String> = emptyList(),
) {
    KoinJavaComponent.get<NavigationEventBus>(NavigationEventBus::class.java)
        .emitEvent(
            NavEvent.Navigate(
                screen = destination,
                alsoClearBackstack = alsoClearBackStack,
                alsoPopUpTo = alsoPopUpTo,
                inclusivePopUp = inclusivePopUp,
                withParameters = withParameters
            )
        )
}

fun navigateTo(
    destination: Graph,
    alsoClearBackStack: Boolean = false,
    alsoPopUpTo: Screen? = null,
    inclusivePopUp: Boolean = true,
    withParameters: List<String> = emptyList(),
) {
    KoinJavaComponent.get<NavigationEventBus>(NavigationEventBus::class.java)
        .emitEvent(
            NavEvent.Navigate(
                graph = destination,
                alsoClearBackstack = alsoClearBackStack,
                alsoPopUpTo = alsoPopUpTo,
                inclusivePopUp = inclusivePopUp,
                withParameters = withParameters
            )
        )
}

fun navigateUp() =
    KoinJavaComponent.get<NavigationEventBus>(NavigationEventBus::class.java).emitEvent(NavEvent.NavigateUp)

fun navigateUpTo(screen: Screen) =
    KoinJavaComponent.get<NavigationEventBus>(NavigationEventBus::class.java).emitEvent(NavEvent.NavigateUpTo(screen))

fun getNavControllerEventBusFlow(): SharedFlow<NavEvent?> =
    KoinJavaComponent.get<NavigationEventBus>(NavigationEventBus::class.java).navEvent
