package br.com.fiap.locawebmailapp.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "T_LCW_CONVIDADO")
data class Convidado (
    @PrimaryKey(autoGenerate = true) var id_convidado: Long = 0,
    var email: String = ""
)