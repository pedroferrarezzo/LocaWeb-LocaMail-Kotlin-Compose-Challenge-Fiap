package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.IdConvidado

@Composable
fun returnColor(option: Int): Color {
    var color: Color = Color.Transparent;
    when (option) {
        1 -> color = colorResource(id = R.color.lcweb_colortodo_1)
        2 -> color = colorResource(id = R.color.lcweb_colortodo_2)
        3 -> color = colorResource(id = R.color.lcweb_colortodo_3)
        4 -> color = colorResource(id = R.color.lcweb_colortodo_4)
    }

    return color
}

fun returnStringRepeatOption(option: Int): String {
    var stringOption = ""
    when (option) {
        1 -> stringOption = "Não repetir"
        2 -> stringOption = "Todos os dias"
    }

    return stringOption
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