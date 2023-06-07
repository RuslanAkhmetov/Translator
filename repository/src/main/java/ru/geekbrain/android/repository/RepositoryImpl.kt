package ru.geekbrain.android.repository

import ru.geekbrain.android.model.dto.WordDto

class RepositoryImpl(private val dataSource: DataSource<List<WordDto>>)
    : Repository<List<WordDto>>{

    override suspend fun getWord(word: String): List<WordDto> =
        dataSource.getWord(word)

}

