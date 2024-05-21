package br.com.fiap.locawebmailapp.utils

import java.text.SimpleDateFormat
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

fun completeStringDateToDate(dateString: String): LocalDate {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.getDefault())
    return LocalDate.parse(dateString, dateFormatter)
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

fun convertTo12Hours(time24: String): String {
    if(validateIfAllDay(time24)) {
        return "Todo dia"
    }

    val format24 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val date = format24.parse(time24)

    val format12 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return format12.format(date!!)
}

fun returnHourAndMinuteSeparate(time: String): List<Int> {
    val initialHour = time.substring(startIndex = 0, endIndex = 2).toInt()
    val initialMinute = time.substring(startIndex = 3, endIndex = 5).toInt()

    return listOf(initialHour, initialMinute)


}

fun returnOneMonthFromDate(data: String): List<String> {
    var startDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val endDate = startDate.plusMonths(1)
    val dates = mutableListOf<String>()

    while (!startDate.isEqual(endDate)) {
        dates.add(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        startDate = startDate.plusDays(1)
    }

    return dates
}

fun validateIfAllDay(hour: String): Boolean {
    return hour.equals("1")
}