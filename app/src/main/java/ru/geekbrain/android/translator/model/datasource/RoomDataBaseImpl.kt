package ru.geekbrain.android.translator.model.datasource

import ru.geekbrain.android.translator.model.data.AppState
import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.room.HistoryDao
import ru.geekbrain.android.translator.model.TranslatorContract
import ru.geekbrain.android.translator.utils.convertWordToEntity
import ru.geekbrain.android.translator.utils.mapHistoryEntityToSearchResult
import ru.geekbrain.android.translator.utils.mapHistotyAll

class RoomDataBaseImpl(private val historyDao: HistoryDao) :
    TranslatorContract.DataSourceLocal<List<Word>> {

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
