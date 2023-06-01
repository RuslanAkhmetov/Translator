package ru.geekbrain.android.translator

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.geekbrain.android.translator.di.application
import ru.geekbrain.android.translator.di.historyScreen
import ru.geekbrain.android.translator.di.mainScreen

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))

            }
        }

    }
