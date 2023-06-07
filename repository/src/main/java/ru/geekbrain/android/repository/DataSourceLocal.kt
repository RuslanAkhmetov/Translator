package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.dto.WordDto

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getAll():List<WordDto>?

}
