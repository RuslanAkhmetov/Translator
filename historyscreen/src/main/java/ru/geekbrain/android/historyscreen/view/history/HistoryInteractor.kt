package ru.geekbrain.android.historyscreen.view.history

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.dto.WordDto
import ru.geekbrain.android.repository.Repository
import ru.geekbrain.android.repository.RepositoryLocal
import ru.geekbrain.android.repository.mapSearchResultToResult

class HistoryInteractor(
    private val repositoryRemote: Repository<List<WordDto>>,
    private val repositoryLocal: RepositoryLocal<List<WordDto>>
) : InteractorLocal<AppState>{

    override suspend fun getWord(searchText: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(
            mapSearchResultToResult(
            if(fromRemoteSource){
                repositoryRemote
            }else {
                repositoryLocal
            }.getWord(searchText)
            )
        )

    override suspend fun getAll(): AppState =
        AppState.Success(repositoryLocal.getAll()?.let { mapSearchResultToResult(it) })

}
