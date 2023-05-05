package ru.geekbrain.android.translator.utils

import ru.geekbrain.android.translator.data.AppState
import ru.geekbrain.android.translator.data.Meaning
import ru.geekbrain.android.translator.data.Word

fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<Word>()
    when (state) {
        is AppState.Success -> {
            val searchResults = state.words
            if (!searchResults.isNullOrEmpty()) {
                for (item in searchResults)
                    parseResult(item, newSearchResults)
            }
        }
        is AppState.Error -> TODO()
        is AppState.Loading -> TODO()
    }

    return AppState.Success(newSearchResults)
}

fun parseResult(word: Word, newSearchResults: ArrayList<Word>) {
    if (!word.text.isNullOrBlank() && !word.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        for (meaning in word.meanings) {
            if (meaning.translation != null && !meaning.translation.text.isNullOrBlank()) {
                newMeanings.add(
                    Meaning(
                        meaning.id,
                        meaning.imageUrl,
                        meaning.partOfSpeechCode,
                        meaning.previewUrl,
                        meaning.soundUrl,
                        meaning.transcription,
                        meaning.translation
                    )
                )
            }
        }
        if(newMeanings.isNotEmpty()){
            newSearchResults.add(Word(word.id, newMeanings, word.text))
        }
    }

}

fun convertMeaningsToString(meanings: List<Meaning>): String{
    var meaningSeparatedBySemicolon = ""
    for ((index, meaning) in meanings.withIndex()){
        meaningSeparatedBySemicolon += if(index+1 != meanings.size){
            String.format("%s%s%s%s", meaning.partOfSpeechCode, " - ", meaning.translation?.text, "; ")
        } else {
            String.format("%s%s%s%s", meaning.partOfSpeechCode, " - ", meaning.translation?.text, ".")
        }
    }
    return meaningSeparatedBySemicolon

}
