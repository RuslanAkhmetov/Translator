package ru.geekbrain.android.repository

interface Repository<T> {

    suspend fun getWord(word: String): T
}
