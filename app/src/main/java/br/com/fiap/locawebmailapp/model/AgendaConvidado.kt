package br.com.fiap.locawebmailapp.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "T_LCW_AGENDA_CONVIDADO",
    foreignKeys =
    [ForeignKey(
        entity = Agenda::class,
        childColumns = ["id_agenda"],
        parentColumns = ["id_agenda"]
    ),
    ForeignKey(
        entity = Convidado::class,
        childColumns = ["id_convidado"],
        parentColumns = ["id_convidado"]
    )
    ]
)
data class AgendaConvidado (
    @PrimaryKey(autoGenerate = true) var id_agenda_convidado: Long = 0,
    var id_agenda : Long = 0,
    var id_convidado : Long = 0,
    var grupo_repeticao: Int = 0
)

@Immutable
data class IdConvidado (
    var id_convidado : Long = 0
)
