package ru.geekbrain.android.translator.model.repo

import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class RepositoryImplementationLocal(private val dataSource: TranslatorContract.DataSourceLocal<List<Word>>)
    : TranslatorContract.RepositoryLocal<List<Word>>{

    override suspend fun getWord(searchText: String): List<Word> =
        dataSource.getWord(searchText)

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getAll():List<Word>? =
        dataSource.getAll()


}

