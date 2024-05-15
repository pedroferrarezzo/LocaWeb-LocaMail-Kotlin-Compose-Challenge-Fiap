package br.com.fiap.locawebmailapp.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun convertMillisToLocalDate(millis: Long) : LocalDate {
    return Instant
        .ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun convertMillisToLocalDateWithFormatter(date: LocalDate, dateTimeFormatter: DateTimeFormatter) : LocalDate {
    val dateInMillis = LocalDate.parse(date.format(dateTimeFormatter), dateTimeFormatter)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    return Instant
        .ofEpochMilli(dateInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun dateToCompleteStringDate(date: LocalDate): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.getDefault())
    val dateInMillis = convertMillisToLocalDateWithFormatter(date, dateFormatter)
    return dateFormatter.format(dateInMillis)
}

fun stringToDate(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.getDefault())
    val date = LocalDate.parse(dateString, inputFormatter)
    return outputFormatter.format(date)
}

fun localDateToMillis(localDate: LocalDate): Long {
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun stringToLocalDate(dateString: String): LocalDate {
    return LocalDate.parse(dateString)
}
