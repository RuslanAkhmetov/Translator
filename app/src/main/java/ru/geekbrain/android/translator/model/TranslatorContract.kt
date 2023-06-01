package ru.geekbrain.android.translator.model

import ru.geekbrain.android.translator.model.data.AppState

class TranslatorContract {

    interface Interactor<T> {
        suspend fun getWord(searchText: String, fromRemoteSource: Boolean) : T

    }

    interface InteractorLocal<T>: Interactor<T>{
        suspend fun getAll(): T?
    }

    interface Repository<T>{
        suspend fun getWord(searchText: String): T
    }

    interface DataSource<T>{
        suspend fun getWord(searchText: String):T
    }

    interface DataSourceLocal<T>: DataSource<T>{
        suspend fun saveToDB(appState: AppState)
        suspend fun getAll(): T?
    }

    interface RepositoryLocal<T>: Repository<T>{
        suspend fun saveToDB(appState: AppState)
        suspend fun getAll(): T?
    }



}