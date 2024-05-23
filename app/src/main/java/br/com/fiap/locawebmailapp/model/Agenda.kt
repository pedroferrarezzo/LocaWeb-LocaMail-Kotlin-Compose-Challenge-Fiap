package br.com.fiap.locawebmailapp.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Immutable
@Entity(tableName = "T_LCW_AGENDA",
    foreignKeys =
    [ForeignKey(
        entity = Usuario::class,
        childColumns = ["id_usuario"],
        parentColumns = ["id_usuario"]
    ),
        ForeignKey(
            entity = Email::class,
            childColumns = ["id_email"],
            parentColumns = ["id_email"]
        )
    ])
data class Agenda(
    @PrimaryKey(autoGenerate = true) var id_agenda: Long = 0,
    var nome: String = "",
    var descritivo: String = "",
    var id_usuario: Long = 0,
    var cor: Int = 0,
    var localizacao: String = "",
    var notificacao: Int = 0,
    var horario: String = "",
    var data: String = "${LocalDate.now()}",
    var proprietario: String = "",
    var evento: Boolean = false,
    var tarefa: Boolean = false,
    var repeticao: Int = 0,
    var grupo_repeticao: Int = 0,
    var visivel: Boolean = true,
    var id_email: Long? = null
)

@Immutable
data class AgendaCor(
    var cor: Int = 0
)

@Immutable
data class AgendaGrupoRepeticao(
    var grupo_repeticao: Int = 0
)