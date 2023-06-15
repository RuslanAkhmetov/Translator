package ru.geekbrain.android.translator


import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.geekbrain.android.model.dto.*
import ru.geekbrain.android.repository.*
import ru.geekbrain.android.translator.view.main.MainInteractor

class MainInteractorTest {

    private val word = "Text"


    lateinit var interactor: MainInteractor

    @Mock
    lateinit var localRepository: RepositoryLocal<List<WordDto>>

    @Mock
    lateinit var mockedRepository:Repository<List<WordDto>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }



    private val listWordDto = listOf(WordDto(
        1, listOf(
            MeaningDto(
                1, "", "", "", "", "",
                TranslationDto("", "TEXT")
            )
        ), ""
    ))

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun mainInteractor_CorrectAnswer() {
            runTest {

                interactor = MainInteractor(mockedRepository, localRepository)
                Mockito.`when`(mockedRepository.getWord(word)).thenReturn(listWordDto)

                interactor.getWord(word, true)

                Mockito.verify(localRepository, times(1)).saveToDB(any())
            }
        }

}