package br.com.fiap.locawebmailapp.model

import androidx.annotation.Nullable
import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "T_LCW_ALTERACAO",
    foreignKeys =
    [ForeignKey(
        entity = Usuario::class,
        childColumns = ["alt_id_usuario"],
        parentColumns = ["id_usuario"]
    ),
        ForeignKey(
            entity = Email::class,
            childColumns = ["alt_id_email"],
            parentColumns = ["id_email"]
        ),
        ForeignKey(
            entity = Pasta::class,
            childColumns = ["id_pasta"],
            parentColumns = ["id_pasta"]
        )
    ])
data class Alteracao(
    @PrimaryKey(autoGenerate = true) var id_alteracao: Long = 0,
    var alt_id_usuario: Long = 1,
    var alt_id_email: Long = 1,
    var id_pasta: Long? = null,
    var importante: Boolean = false,
    var lido: Boolean = false,
    var arquivado: Boolean = false,
    var excluido: Boolean = false,
    var spam: Boolean = false

)