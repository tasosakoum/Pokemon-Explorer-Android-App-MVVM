package com.example.domain.models.navigation

sealed interface Screen {
    val route: String
    val parameters: List<String>
        get() = emptyList()

    companion object {
        fun getScreenFromRoute(route: String?): Screen? {
            return Screen::class.sealedSubclasses.map {
                it.objectInstance
            }.find { it?.route == route }
        }
    }
}