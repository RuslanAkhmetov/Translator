package ru.geekbrain.android.translator.view.main

import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class MainInteractor(
    private val remoteRepository: TranslatorContract.Repository<List<Word>>,
    private val localRepository: TranslatorContract.RepositoryLocal<List<Word>>
) : TranslatorContract.Interactor<AppState> {

    override suspend fun getWord(searchText: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getWord(searchText))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(localRepository.getWord(searchText))
        }
        return appState
    }

}