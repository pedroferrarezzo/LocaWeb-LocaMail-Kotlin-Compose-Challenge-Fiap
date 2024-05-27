package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.IdConvidado
import br.com.fiap.locawebmailapp.model.RespostaEmail

fun listaParaString(lista: List<String>): String {
    return lista.withIndex().joinToString(", ") { it.value }
}

fun stringParaLista(str: String): List<String> {
    return str.split(", ")
}

fun atualizarTodosDestinatariosList(todosDestinatarios: ArrayList<String>, email: Email, respostaEmailList: List<RespostaEmail>){

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

fun returnListConvidado(
    listIdConvidado: List<IdConvidado>,
    convidadoRepository: ConvidadoRepository
): SnapshotStateList<Convidado> {

    val list = mutableStateListOf<Convidado>()

    for (id in listIdConvidado) {
        list.add(convidadoRepository.listarConvidadoPorId(id.id_convidado))
    }

    return list
}