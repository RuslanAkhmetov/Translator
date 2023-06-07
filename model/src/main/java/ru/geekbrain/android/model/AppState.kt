package ru.geekbrain.android.model

import ru.geekbrain.android.model.userdata.Word

sealed class AppState {
    data class Success(val words: List<Word>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}

