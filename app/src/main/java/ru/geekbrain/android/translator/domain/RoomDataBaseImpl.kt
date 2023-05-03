package ru.geekbrain.android.translator.domain

import io.reactivex.Observable
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class RoomDataBaseImpl : TranslatorContract.DataSource<List<Word>> {
    override fun getWord(searchText: String): Observable<List<Word>> {
        TODO("Not yet implemented")
    }

}
