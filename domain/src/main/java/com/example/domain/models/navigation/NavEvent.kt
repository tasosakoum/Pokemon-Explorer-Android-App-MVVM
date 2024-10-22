package com.example.domain.models.navigation

sealed interface NavEvent {
    class Navigate(
        val route: String,
        val alsoClearBackstack: Boolean,
        val alsoPopUpTo: Screen?,
        val inclusivePopUp: Boolean,
        val withParameters: List<String>,
    ) : NavEvent {

        constructor(
            graph: Graph,
            alsoClearBackstack: Boolean,
            alsoPopUpTo: Screen? = null,
            inclusivePopUp: Boolean = true,
            withParameters: List<String> = emptyList(),
        ) : this(
            route = graph.route,
            alsoClearBackstack = alsoClearBackstack,
            alsoPopUpTo = alsoPopUpTo,
            inclusivePopUp = inclusivePopUp,
            withParameters = withParameters
        )

        constructor(
            screen: Screen,
            alsoClearBackstack: Boolean,
            alsoPopUpTo: Screen? = null,
            inclusivePopUp: Boolean = true,
            withParameters: List<String> = emptyList(),
        ) : this(
            route = screen.route,
            alsoClearBackstack = alsoClearBackstack,
            alsoPopUpTo = alsoPopUpTo,
            inclusivePopUp = inclusivePopUp,
            withParameters = withParameters
        )
    }
    data object NavigateUp : NavEvent
    data object Logout : NavEvent
    class NavigateUpTo(val screen: Screen) : NavEvent
}