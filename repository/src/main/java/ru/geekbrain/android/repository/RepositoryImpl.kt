package ru.geekbrain.android.repository

import ru.geekbrain.android.model.Word

class RepositoryImpl(private val dataSource: DataSource<List<Word>>)
    : Repository<List<Word>>{

    override suspend fun getWord(word: String): List<Word> =
        dataSource.getWord(word)

}

