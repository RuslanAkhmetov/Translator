package ru.geekbrain.android.translator.data


import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("note")
    val note: String,
    @SerializedName("text")
    val text: String
)