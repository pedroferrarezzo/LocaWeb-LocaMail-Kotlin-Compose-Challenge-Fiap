package br.com.fiap.locawebmailapp.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Immutable
@Entity(tableName = "T_LCW_ANEXO_RESPOSTA_EMAIL",
    foreignKeys =
    [ForeignKey(
        entity = RespostaEmail::class,
        childColumns = ["id_resposta_email"],
        parentColumns = ["id_resposta_email"]
    )
    ])

data class AnexoRespostaEmail(
    @PrimaryKey(autoGenerate = true) var id_anexo_resposta_email: Long = 0,
    var id_resposta_email: Long = 0,
    var anexo: ByteArray = ByteArray(0)
)