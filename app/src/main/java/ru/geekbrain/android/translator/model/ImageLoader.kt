package ru.geekbrain.android.translator.model

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}