package com.example.domain.models.navigation

sealed interface NavEvent {
    class Navigate(
        val screen: Screen,
        val alsoClearBackstack: Boolean,
        val alsoPopUpTo: Screen?,
        val inclusivePopUp: Boolean,
    ) : NavEvent {
        constructor(
            graph: Graph,
            alsoClearBackstack: Boolean,
            alsoPopUpTo: Screen? = null,
            inclusivePopUp: Boolean = true,
        ) : this(
            screen = graph.startDestination,
            alsoClearBackstack = alsoClearBackstack,
            alsoPopUpTo = alsoPopUpTo,
            inclusivePopUp = inclusivePopUp,
        )
    }
    data object NavigateUp : NavEvent
    data object Logout : NavEvent
    class NavigateUpTo(val screen: Screen) : NavEvent
}