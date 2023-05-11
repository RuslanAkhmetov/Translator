package ru.geekbrain.android.translator.model

import ru.geekbrain.android.translator.data.AppState

class TranslatorContract {

    interface Interactor<T> {
        suspend fun getWord(searchText: String, fromRemoteSource: Boolean) : T
    }

    interface Repository<T>{
        suspend fun getWord(searchText: String): T
    }

    interface DataSource<T>{
        suspend fun getWord(searchText: String):T
    }



}