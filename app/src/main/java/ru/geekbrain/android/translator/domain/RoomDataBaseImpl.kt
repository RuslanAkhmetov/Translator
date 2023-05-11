package ru.geekbrain.android.translator.domain

import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class RoomDataBaseImpl : TranslatorContract.DataSource<List<Word>> {
    override suspend fun getWord(searchText: String): List<Word> {
        TODO("Not yet implemented")
    }

}
