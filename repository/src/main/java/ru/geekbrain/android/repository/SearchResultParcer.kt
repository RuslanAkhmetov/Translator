package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.Meaning
import ru.geekbrain.android.model.Translation
import ru.geekbrain.android.model.Word
import ru.geekbrain.android.repository.room.HistoryEntity

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

        else -> {}
    }

    return AppState.Success(newSearchResults)
}

fun parseLocalSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, false))
}

fun mapResult(appState: AppState, onLine: Boolean): List<Word>? {
    val newSearchResults = arrayListOf<Word>()
    when (appState) {
        is AppState.Success -> {
            getSuccessResultData(appState, onLine, newSearchResults)
        }
        else -> {}
    }
    return newSearchResults
}

fun getSuccessResultData(
    appState: AppState.Success,
    online: Boolean,
    newSearchResults: ArrayList<Word>
) {
    val wordList: List<Word> = appState.words as List<Word>
    if (wordList.isNotEmpty()) {
        if (online) {
            for (result in wordList) {
                parseOnlineResult(result, newSearchResults)
            }
        } else {
            for (result in wordList) {
                newSearchResults.add(Word(result.id, result.meanings, result.text))
            }
        }
    }
}


fun parseOnlineResult(result: Word, newSearchResults: ArrayList<Word>) {
    if (result.id != 0 && !result.meanings.isNullOrEmpty()) {
        val newMeaning = arrayListOf<Meaning>()
        for (meaning in result.meanings!!) {
            if (meaning.translation != null && !meaning.translation.text.isNullOrBlank()) {
                newMeaning.add(
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
            if (newMeaning.isNotEmpty()) {
                newSearchResults.add(Word(result.id, newMeaning, result.text))
            }
        }
    }
}


fun parseResult(word: Word, newSearchResults: ArrayList<Word>) {
    if (!word.text.isNullOrBlank() && !word.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        for (meaning in word.meanings!!) {
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
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(Word(word.id, newMeanings, word.text))
        }
    }

}


fun mapHistoryEntityToSearchResult(wordList: List<HistoryEntity>): List<Word> {
    val words = ArrayList<Word>()
    if (!wordList.isNullOrEmpty()) {
        for (word in wordList) {
            words.add(Word(word.id, null, word.word!!))
        }
    }
    return words
}

fun mapHistotyAll(all: List<HistoryEntity>): List<Word> {
    val words = ArrayList<Word>()
    if (!all.isNullOrEmpty()) {
        for (word in all) {
            words.add(
                Word(
                    word.id,
                    word.description?.let {
                    listOf<Meaning>(
                        Meaning(0, "", "", "", "", "",
                             Translation("", it) ))
                                          },
                        word.word!!
                    )
                )
        }
    }
    return words
}

fun convertWordToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.words
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrBlank()) {
                null
            } else {
                HistoryEntity(searchResult[0].id, searchResult[0].text,
                    searchResult[0].meanings?.let { convertMeaningsToString(it) })
            }
        }
        else -> null
    }
}

fun convertMeaningsToString(meanings: List<Meaning>): String {
    var meaningSeparatedBySemicolon = ""
    for ((index, meaning) in meanings.withIndex()) {
        meaningSeparatedBySemicolon += if (index + 1 != meanings.size) {
            String.format(
                "%s%s%s%s",
                meaning.partOfSpeechCode,
                " - ",
                meaning.translation?.text,
                "; "
            )
        } else {
            String.format(
                "%s%s%s%s",
                meaning.partOfSpeechCode,
                " - ",
                meaning.translation?.text,
                "."
            )
        }
    }
    return meaningSeparatedBySemicolon

}
