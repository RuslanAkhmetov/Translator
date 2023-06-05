package ru.geekbrain.android.repository

interface DataSource<T> {

    suspend fun getWord(word: String): T
}
