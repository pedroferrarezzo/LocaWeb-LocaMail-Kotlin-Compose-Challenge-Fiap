package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.text.SimpleDateFormat
import java.util.Locale


fun convertTo12Hours(time24: String): String {
    if(validateIfAllDay(time24)) {
        return "Todo dia"
    }

    val format24 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val date = format24.parse(time24)

    val format12 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return format12.format(date!!)
}