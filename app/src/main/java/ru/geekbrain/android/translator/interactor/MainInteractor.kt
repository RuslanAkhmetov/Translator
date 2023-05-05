package ru.geekbrain.android.translator.interactor

import io.reactivex.Observable
import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.di.NAME_LOCAL
import ru.geekbrain.android.translator.di.NAME_REMOTE
import ru.geekbrain.android.translator.model.TranslatorContract
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: TranslatorContract.Repository<List<Word>>,
    @Named(NAME_LOCAL) val localRepository: TranslatorContract.Repository<List<Word>>
): TranslatorContract.Interactor<AppState> {

    override fun getWord(searchText: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource){
            remoteRepository.getWord(searchText).map { AppState.Success(it) }
        } else{
            localRepository.getWord(searchText).map { AppState.Success(it) }
        }
    }

}