package br.com.fiap.locawebmailapp.utils

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+\$".toRegex()
    return emailRegex.matches(email)
}