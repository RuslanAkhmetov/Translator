package ru.geekbrain.android.translator.domain

import io.reactivex.Observable
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.ui.TranslatorContract

class MainInteractor(
    private val remoteRepository: TranslatorContract.Repository<List<Word>>,
    private val localRepository: TranslatorContract.Repository<List<Word>>
): TranslatorContract.Interactor<AppState> {
    override fun getWord(searchText: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource){
            remoteRepository.getWord(searchText).map { AppState.Success(it) }
        } else{
            localRepository.getWord(searchText).map { AppState.Success(it) }
        }
    }

}