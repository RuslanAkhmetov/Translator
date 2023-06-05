package ru.geekbrain.android.model


import com.google.gson.annotations.SerializedName

class Word(
    @SerializedName("id")
    val id: Int,
    @SerializedName("meanings")
    val meanings: List<Meaning>?,
    @SerializedName("text")
    val text: String
)