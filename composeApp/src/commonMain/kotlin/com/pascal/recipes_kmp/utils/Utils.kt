package com.pascal.recipes_kmp.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import com.pascal.recipes_kmp.utils.Constant.FORMAT_DATE

fun generateRandomChar(): String {
    return ('a'..'z').random().toString()
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun reFormatDate(date: String?): LocalDate? {
    if (date.isNullOrBlank()) {
        return null
    }

    return try {
        return LocalDate.parse(
            input = date,
            format = LocalDate.Format { byUnicodePattern(FORMAT_DATE) }
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getCurrentDate(): String {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
}