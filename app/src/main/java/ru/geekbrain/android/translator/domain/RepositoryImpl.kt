package ru.geekbrain.android.translator.domain

import io.reactivex.Observable
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class RepositoryImpl(private val dataSource: TranslatorContract.DataSource<List<Word>>)
    : TranslatorContract.Repository<List<Word>>{

    override fun getWord(searchText: String): Observable<List<Word>> {
        return dataSource.getWord(searchText)
    }

}

