package ru.geekbrain.android.model.userdata

data class Meaning (
    val translatedMeaning: TranslatedMeaning = TranslatedMeaning(),
    val partOfSpeechCode: String="",
    val imageUrl: String = ""
        )


