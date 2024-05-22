package br.com.fiap.locawebmailapp.utils

import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.RespostaEmail

fun listaParaString(lista: List<String>): String {
    return lista.withIndex().joinToString(", ") { it.value }
}

fun stringParaLista(str: String): List<String> {
    return str.split(", ")
}

fun atualizarTodosDestinatarios(todosDestinatarios: ArrayList<String>, email: Email, respostaEmailList: List<RespostaEmail>){

    todosDestinatarios.addAll(stringParaLista(email.destinatario))
    todosDestinatarios.addAll(stringParaLista(email.cc))
    todosDestinatarios.addAll(stringParaLista(email.cco))

    for (resposta in respostaEmailList) {
        for (destinatario in stringParaLista(resposta.destinatario)) {
            if (!todosDestinatarios.contains(destinatario)) {
                todosDestinatarios.add(destinatario)
            }
        }

        for (cc in stringParaLista(resposta.cc)) {
            if (!todosDestinatarios.contains(cc)) {
                todosDestinatarios.add(cc)
            }
        }

        for (cco in stringParaLista(resposta.cco)) {
            if (!todosDestinatarios.contains(cco)) {
                todosDestinatarios.add(cco)
            }
        }
    }
}
