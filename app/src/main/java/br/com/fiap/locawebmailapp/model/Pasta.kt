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
@Entity(tableName = "T_LCW_PASTA",
    foreignKeys =
    [ForeignKey(
        entity = Usuario::class,
        childColumns = ["id_usuario"],
        parentColumns = ["id_usuario"]
    )
    ])
data class Pasta(
    @PrimaryKey(autoGenerate = true) var id_pasta: Long = 0,
    var id_usuario: Long = 0,
    var nome: String = ""

)