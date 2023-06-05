package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.Word

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<Word>>)
    : RepositoryLocal<List<Word>>{

    override suspend fun getWord(searchText: String): List<Word> =
        dataSource.getWord(searchText)

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getAll():List<Word>? =
        dataSource.getAll()


}

