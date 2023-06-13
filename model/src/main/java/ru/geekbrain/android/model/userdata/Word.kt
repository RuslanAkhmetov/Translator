package ru.geekbrain.android.model.userdata

data class Word (
    val id: Int = 0,
    var text: String = "",
    val meanings: List<Meaning> = listOf()
)