package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.dto.WordDto


interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getAll(): List<WordDto>?
}
