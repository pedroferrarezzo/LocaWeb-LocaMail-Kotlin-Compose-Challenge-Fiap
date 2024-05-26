package br.com.fiap.locawebmailapp.model.ai

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Usuario

@Immutable
@Entity(tableName = "T_LCW_AI_QUESTION",
    foreignKeys =
    [ForeignKey(
        entity = Email::class,
        childColumns = ["id_email"],
        parentColumns = ["id_email"]
    )
    ])
data class AiQuestion(
    @PrimaryKey(autoGenerate = true) var id_question: Long = 0,
    val id_email: Long = 0,
    var pergunta: String,
)