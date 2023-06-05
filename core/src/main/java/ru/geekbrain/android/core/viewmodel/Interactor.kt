package ru.geekbrain.android.core.viewmodel

interface Interactor<T> {
    suspend fun getWord(searchText: String, fromRemoteSource: Boolean) : T

}