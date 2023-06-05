package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.Word
import ru.geekbrain.android.repository.room.HistoryDao

class RoomDataBaseImpl(private val historyDao: HistoryDao) :
    DataSourceLocal<List<Word>> {

    override suspend fun getWord(searchText: String): List<Word>{
        return mapHistoryEntityToSearchResult(historyDao.all())
    }


    override suspend fun saveToDB(appState: AppState) {
        convertWordToEntity(appState)?.let{
            historyDao.insert(it)
        }
    }

    override suspend fun getAll(): List<Word>? {
        return mapHistotyAll(historyDao.all())
    }




}
