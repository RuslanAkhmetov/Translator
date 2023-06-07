package ru.geekbrain.android.model.dto


import com.google.gson.annotations.SerializedName

data class TranslationDto(
    @SerializedName("note")
    val note: String,
    @SerializedName("text")
    val text: String
)