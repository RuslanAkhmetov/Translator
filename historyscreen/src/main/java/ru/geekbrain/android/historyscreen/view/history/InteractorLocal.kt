package ru.geekbrain.android.historyscreen.view.history

import ru.geekbrain.android.core.viewmodel.Interactor

interface InteractorLocal<T>: Interactor<T> {

    suspend fun getAll(): T?
}
