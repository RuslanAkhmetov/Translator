package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.dto.WordDto

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<WordDto>>)
    : RepositoryLocal<List<WordDto>>{

    override suspend fun getWord(searchText: String): List<WordDto> =
        dataSource.getWord(searchText)

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getAll():List<WordDto>? =
        dataSource.getAll()


}

