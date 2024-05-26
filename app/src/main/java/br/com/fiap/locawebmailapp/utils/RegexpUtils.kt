package br.com.fiap.locawebmailapp.utils

fun validateEmail(email: String): Boolean {
    val emailRegex = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+\$".toRegex()
    return emailRegex.matches(email)
}

fun validatePassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}\$".toRegex()
    return passwordRegex.matches(password)
}