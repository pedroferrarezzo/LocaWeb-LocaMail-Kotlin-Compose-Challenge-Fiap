package br.com.fiap.locawebmailapp.utils

import java.io.File
import java.util.Properties

fun retornaApiToken(): String {
    val properties = Properties().apply {
        val inputStream = File("config.properties").inputStream()
        load(inputStream)
    }
    val apiToken = properties.getProperty("API_TOKEN")

    return apiToken
}