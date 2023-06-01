package ru.geekbrain.android.translator.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id"])])
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name="id")
    val id: Int,

    @ColumnInfo(name="word")
    val word: String?,

    @ColumnInfo(name = "description")
    val description: String?

)