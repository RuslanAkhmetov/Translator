package ru.geekbrain.android.translator.data

sealed class AppState{
    data class Success(val words: List<Word>?): AppState()
    data class Error (val error: Throwable): AppState()
    data class Loading(val progress: Int?) :AppState()
}
