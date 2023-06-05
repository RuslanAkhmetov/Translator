package ru.geekbrain.android.historyscreen.view.history

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.Word
import ru.geekbrain.android.repository.Repository
import ru.geekbrain.android.repository.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<Word>>,
    private val repositoryLocal: RepositoryLocal<List<Word>>
) : InteractorLocal<AppState>{

    override suspend fun getWord(searchText: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(
            if(fromRemoteSource){
                repositoryRemote
            }else {
                repositoryLocal
            }.getWord(searchText)
        )

    override suspend fun getAll(): AppState =
        AppState.Success( repositoryLocal.getAll())

}
