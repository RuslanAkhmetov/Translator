package ru.geekbrain.android.translator.view.main

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.Word
import ru.geekbrain.android.repository.Repository
import ru.geekbrain.android.repository.RepositoryLocal
import ru.geekbrain.android.core.viewmodel.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<Word>>,
    private val localRepository: RepositoryLocal<List<Word>>
) : Interactor<AppState> {

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