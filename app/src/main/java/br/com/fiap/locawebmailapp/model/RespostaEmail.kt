package br.com.fiap.locawebmailapp.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Immutable
@Entity(tableName = "T_LCW_RESPOSTA_EMAIL",
    foreignKeys =
    [ForeignKey(
        entity = Email::class,
        childColumns = ["id_email"],
        parentColumns = ["id_email"]
    ),
        ForeignKey(
            entity = Usuario::class,
            childColumns = ["id_usuario"],
            parentColumns = ["id_usuario"]
        )
    ])
data class RespostaEmail(
    @PrimaryKey(autoGenerate = true) var id_resposta_email: Long = 0,
    var id_email: Long = 0,
    var id_usuario: Long = 0,
    var remetente: String = "",
    var destinatario: String = "",
    var cc: String = "",
    var cco: String = "",
    var assunto: String = "",
    var corpo: String = "",
    var editavel: Boolean = false,
    var enviado: Boolean = false,
    var horario: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
    var data: String = "${LocalDate.now()}"
)