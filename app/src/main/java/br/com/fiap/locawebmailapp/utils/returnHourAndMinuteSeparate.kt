package br.com.fiap.locawebmailapp.utils

fun returnHourAndMinuteSeparate(time: String): List<Int> {
    val initialHour = time.substring(startIndex = 0, endIndex = 2).toInt()
    val initialMinute = time.substring(startIndex = 3, endIndex = 5).toInt()

    return listOf(initialHour, initialMinute)


}