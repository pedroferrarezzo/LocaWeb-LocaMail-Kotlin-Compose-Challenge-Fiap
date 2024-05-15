package br.com.fiap.locawebmailapp.utils

fun returnStringRepeatOption(option: Int): String {
    var stringOption = ""
    when (option) {
        1 -> stringOption = "Não repetir"
        2 -> stringOption = "Todos os dias"
    }

    return stringOption
}