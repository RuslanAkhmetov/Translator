package ru.geekbrain.android.model.dto

import com.google.gson.annotations.SerializedName

data class WordDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("meanings")
    val meanings: List<MeaningDto>?,
    @SerializedName("text")
    val text: String
)