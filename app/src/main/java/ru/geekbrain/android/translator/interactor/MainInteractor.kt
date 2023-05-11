package ru.geekbrain.android.translator.interactor

import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class MainInteractor(
    val remoteRepository: TranslatorContract.Repository<List<Word>>,
    val localRepository: TranslatorContract.Repository<List<Word>>
) : TranslatorContract.Interactor<AppState> {

    override suspend fun getWord(searchText: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository.getWord(searchText)
            } else {
                localRepository.getWord(searchText)
            }
        )
    }

}