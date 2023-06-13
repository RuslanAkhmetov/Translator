package ru.geekbrain.android.translator


import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.translator.view.main.MainInteractor

class MainInteractorTest {
    @Test
    suspend fun MainInteractor_CorrectAnswer_ReturnTrue() {
        val interactor: MainInteractor by inject(MainInteractor::class.java)
        val appState = interactor.getWord("тест", true)
        when (appState) {
            is AppState.Success -> {
                assertEquals(appState.words?.get(0)?.text, "test")
            }
            else -> assertFalse(true)
        }
    }

}