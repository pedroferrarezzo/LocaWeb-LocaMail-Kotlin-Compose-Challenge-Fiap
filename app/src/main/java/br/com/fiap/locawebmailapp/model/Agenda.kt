package br.com.fiap.locawebmailapp.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Immutable
@Entity(tableName = "T_LCW_AGENDA")
data class Agenda(
    @PrimaryKey(autoGenerate = true) var id_agenda: Long = 0,
    var nome: String = "",
    var descritivo: String = "",
    var cor: Int = 0,
    var localizacao: String = "",
    var notificacao: Int = 0,
    var horario: String = "",
    var data: String = "${LocalDate.now()}",
    var proprietario: String = "",
    var evento: Boolean = false,
    var tarefa: Boolean = false,
    var repeticao: Int = 0,
    var grupo_repeticao: Int = 0

)

@Immutable
data class AgendaCor(
    var cor: Int = 0
)

@Immutable
data class AgendaGrupoRepeticao(
    var grupo_repeticao: Int = 0
)