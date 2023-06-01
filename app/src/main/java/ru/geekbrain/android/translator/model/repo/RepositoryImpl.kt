package ru.geekbrain.android.translator.model.repo

import ru.geekbrain.android.translator.model.data.Word
import ru.geekbrain.android.translator.model.TranslatorContract

class RepositoryImpl(private val dataSource: TranslatorContract.DataSource<List<Word>>)
    : TranslatorContract.Repository<List<Word>>{

    override suspend fun getWord(searchText: String): List<Word> =
        dataSource.getWord(searchText)

}

