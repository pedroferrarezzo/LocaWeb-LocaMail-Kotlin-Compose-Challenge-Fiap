package br.com.fiap.locawebmailapp.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Immutable
@Entity(tableName = "T_LCW_ANEXO",
    foreignKeys =
    [ForeignKey(
        entity = Email::class,
        childColumns = ["id_email"],
        parentColumns = ["id_email"]
    )
    ])

data class Anexo(
    @PrimaryKey(autoGenerate = true) var id_anexo: Long = 0,
    var id_email: Long = 0,
    var anexo: ByteArray = ByteArray(0)
)