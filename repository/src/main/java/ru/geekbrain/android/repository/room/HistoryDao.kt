package ru.geekbrain.android.repository.room

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    suspend fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE word Like :searchWord")
    suspend fun getDataByWord(searchWord: String):List<HistoryEntity>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertAll(entityList: List<HistoryEntity>)

    @Update
    suspend fun update(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)

}