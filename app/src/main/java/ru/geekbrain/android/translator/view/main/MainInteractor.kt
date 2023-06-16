package ru.geekbrain.android.translator.view.main

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.dto.WordDto
import ru.geekbrain.android.repository.Repository
import ru.geekbrain.android.repository.RepositoryLocal
import ru.geekbrain.android.core.viewmodel.Interactor
import ru.geekbrain.android.model.userdata.Word
import ru.geekbrain.android.repository.mapSearchResultToResult

class MainInteractor(
    private val remoteRepository: Repository<List<WordDto>>,
    private val localRepository: RepositoryLocal<List<WordDto>>
) : Interactor<AppState> {

    override suspend fun getWord(searchText: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            val response: List<Word> = mapSearchResultToResult(remoteRepository.getWord(searchText))
            if (!response.isNullOrEmpty()) {
                appState = AppState.Success(response)
                localRepository.saveToDB(appState)
            } else {
                appState = AppState.Error(Throwable("Response is empty"))
            }
        } else {
            appState =
                AppState.Success(mapSearchResultToResult(localRepository.getWord(searchText)))
        }
        return appState
    }

}