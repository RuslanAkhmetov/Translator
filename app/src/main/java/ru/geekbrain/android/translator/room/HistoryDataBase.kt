package ru.geekbrain.android.translator.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDataBase: RoomDatabase() {
    abstract val historyDao: HistoryDao
}