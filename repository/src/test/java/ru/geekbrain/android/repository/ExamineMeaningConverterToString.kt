package ru.geekbrain.android.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.geekbrain.android.model.userdata.Meaning
import ru.geekbrain.android.model.userdata.TranslatedMeaning

class ExamineMeaningConverterToString {
    @Test
    fun examine_Meaning_Converter_ReturnTrue(){
        val meaningList = listOf(
            Meaning(TranslatedMeaning("meaningFirst"),"n",""),
            Meaning(TranslatedMeaning("meaningTwo"), "a",""))

        assertEquals(convertMeaningsToString(meaningList).toString(), "n - meaningFirst; a - meaningTwo.")
    }
}