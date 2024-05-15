package br.com.fiap.locawebmailapp.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

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