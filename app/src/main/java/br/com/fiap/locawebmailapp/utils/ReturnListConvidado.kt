package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.IdConvidado

fun returnListConvidado(
    listIdConvidado: List<IdConvidado>,
    convidadoRepository: ConvidadoRepository): SnapshotStateList<Convidado> {

    val list = mutableStateListOf<Convidado>()

    for (id in listIdConvidado) {
        list.add(convidadoRepository.listarConvidadoPorId(id.id_convidado))
    }

    return list
}