package ru.geekbrain.android.repository

import ru.geekbrain.android.model.AppState
import ru.geekbrain.android.model.dto.MeaningDto
import ru.geekbrain.android.model.dto.TranslationDto
import ru.geekbrain.android.model.dto.WordDto
import ru.geekbrain.android.model.userdata.Meaning
import ru.geekbrain.android.model.userdata.TranslatedMeaning
import ru.geekbrain.android.model.userdata.Word
import ru.geekbrain.android.repository.room.HistoryEntity

fun mapSearchResultToResult(searchResults: List<WordDto>): List<Word> {
    return searchResults.map {
        var meanings: List<Meaning> = listOf()
        it.meanings?.let {
            meanings = it.map { meaningDto ->
                Meaning(
                    TranslatedMeaning(
                        meaningDto?.translation?.text ?: ""
                    ),
                    meaningDto.partOfSpeechCode,
                    meaningDto?.imageUrl ?: ""
                )

            }
        }
        Word(it.id,it.text ?: "", meanings)
    }
}

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

fun mapResult(appState: AppState, onLine: Boolean): List<Word> {
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
    val wordDtoLists: List<Word> = appState.words as List<Word>
    if (wordDtoLists.isNotEmpty()) {
        if (online) {
            for (result in wordDtoLists) {
                parseOnlineResult(result, newSearchResults)
            }
        } else {
            for (result in wordDtoLists) {
                newSearchResults.add(Word(result.id, result.text, result.meanings))
            }
        }
    }
}


fun parseOnlineResult(result: Word, newSearchResults: ArrayList<Word>) {
    if (result.text.isNotBlank() && result.meanings.isNotEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        newMeanings.addAll(result.meanings.filter {
            it.translatedMeaning.translatedMeaning.isNotBlank()
        })
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(
                Word(
                    result.id,
                    result.text,
                    newMeanings
                )
            )
        }
    }
}


fun parseResult(wordDto: Word, newSearchResults: ArrayList<Word>) {
    if (!wordDto.text.isNullOrBlank() && !wordDto.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        for (meaning in wordDto.meanings!!) {
            if (meaning.translatedMeaning != null && !meaning.translatedMeaning.translatedMeaning.isNullOrBlank()) {
                newMeanings.add(
                    Meaning(
                        meaning.translatedMeaning,
                        meaning.partOfSpeechCode,
                        meaning.imageUrl,
                    )
                )
            }
        }
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(Word(wordDto.id, wordDto.text, newMeanings))
        }
    }

}


fun mapHistoryEntityToSearchResult(wordList: List<HistoryEntity>): List<WordDto> {
    val wordDtos = ArrayList<WordDto>()
    if (!wordList.isNullOrEmpty()) {
        for (word in wordList) {
            wordDtos.add(WordDto(word.id, null, word.word!!))
        }
    }
    return wordDtos
}

fun mapHistotyAll(all: List<HistoryEntity>): List<WordDto> {
    val wordDtos = ArrayList<WordDto>()
    if (!all.isNullOrEmpty()) {
        for (word in all) {
            wordDtos.add(
                WordDto(
                    word.id,
                    word.description?.let {
                        listOf<MeaningDto>(
                            MeaningDto(
                                0, "", "", "", "", "",
                                TranslationDto("", it)
                            )
                        )
                    },
                    word.word!!
                )
            )
        }
    }
    return wordDtos
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
                meaning.translatedMeaning.translatedMeaning,
                "; "
            )
        } else {
            String.format(
                "%s%s%s%s",
                meaning.partOfSpeechCode,
                " - ",
                meaning.translatedMeaning.translatedMeaning,
                "."
            )
        }
    }
    return meaningSeparatedBySemicolon

}
