package br.com.fiap.locawebmailapp.utils

fun listaParaString(lista: List<String>): String {
    return lista.withIndex().joinToString(", ") { it.value }
}

fun stringParaLista(str: String): List<String> {
    return str.split(", ")
}
