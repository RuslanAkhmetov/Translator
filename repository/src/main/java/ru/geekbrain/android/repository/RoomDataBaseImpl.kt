package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.dto.WordDto
import ru.geekbrain.android.repository.room.HistoryDao

class RoomDataBaseImpl(private val historyDao: HistoryDao) :
    DataSourceLocal<List<WordDto>> {

    override suspend fun getWord(searchText: String): List<WordDto>{
        return mapHistoryEntityToSearchResult(historyDao.all())
    }


    override suspend fun saveToDB(appState: AppState) {
        convertWordToEntity(appState)?.let{
            historyDao.insert(it)
        }
    }

    override suspend fun getAll(): List<WordDto>? {
        return mapHistotyAll(historyDao.all())
    }




}
