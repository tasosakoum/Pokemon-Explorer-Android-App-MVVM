package com.example.data.di

import com.example.data.event_bus.NavigationEventBus
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataKoinModule = module {
    singleOf(::NavigationEventBus)
}