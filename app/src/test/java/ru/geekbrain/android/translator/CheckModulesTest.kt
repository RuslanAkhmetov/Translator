package ru.geekbrain.android.translator

import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import ru.geekbrain.android.translator.di.application
import ru.geekbrain.android.translator.di.descriptionScreen
import ru.geekbrain.android.translator.di.historyScreen
import ru.geekbrain.android.translator.di.mainScreen

class CheckModulesTest: KoinTest {
    @Test
    fun checkAllModules() {
        application.verify()
        mainScreen.verify()
        historyScreen.verify()
        descriptionScreen.verify()
    }
}