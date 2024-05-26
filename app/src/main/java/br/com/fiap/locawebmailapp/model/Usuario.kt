package br.com.fiap.locawebmailapp.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Immutable
@Entity(tableName = "T_LCW_USUARIO",
    indices = [Index(value = ["email"], unique = true)])
data class Usuario(
    @PrimaryKey(autoGenerate = true) var id_usuario: Long = 0,
    @ColumnInfo(name = "email") var email: String,
    var nome: String,
    var senha: String,
    var autenticado: Boolean,
    var profile_image: ByteArray = ByteArray(10),
    var selected_user: Boolean
)