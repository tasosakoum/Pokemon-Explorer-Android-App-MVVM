package com.example.pokemonexplorer

import android.app.Application
import com.example.data.di.dataKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(
                listOf(
                    dataKoinModule
                )
            )
        }
    }
}