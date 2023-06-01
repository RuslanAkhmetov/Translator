package ru.geekbrain.android.translator.view.history

import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class HistoryInteractor(
    private val repositoryRemote: TranslatorContract.Repository<List<Word>>,
    private val repositoryLocal: TranslatorContract.RepositoryLocal<List<Word>>
) : TranslatorContract.InteractorLocal<AppState>{

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
