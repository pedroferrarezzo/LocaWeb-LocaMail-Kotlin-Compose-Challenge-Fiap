package br.com.fiap.locawebmailapp.utils

import java.security.MessageDigest

fun generateSha256(input: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    val bytes = md.digest(input.toByteArray())
    return bytes.fold("", { str, it -> str + "%02x".format(it) })
}