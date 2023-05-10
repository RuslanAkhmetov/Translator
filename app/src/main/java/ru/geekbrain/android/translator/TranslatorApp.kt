package ru.geekbrain.android.translator

import android.app.Application
import org.koin.core.context.startKoin
import ru.geekbrain.android.translator.di.application
import ru.geekbrain.android.translator.di.mainScreen

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(listOf(application, mainScreen))

            }
        }
    }
