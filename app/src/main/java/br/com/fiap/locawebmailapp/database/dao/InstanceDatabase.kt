package br.com.fiap.locawebmailapp.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Anexo
import br.com.fiap.locawebmailapp.model.AnexoRespostaEmail
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.model.ai.AiQuestion

@Database(
    entities = [Agenda::class, Convidado::class, AgendaConvidado::class, Email::class, Anexo::class, Usuario::class, Alteracao::class, Pasta::class, RespostaEmail::class, AnexoRespostaEmail::class, AiQuestion::class],
    version = 50
)
abstract class InstanceDatabase : RoomDatabase() {

    abstract fun agendaDao(): AgendaDao
    abstract fun convidadoDao(): ConvidadoDao
    abstract fun agendaConvidadoDao(): AgendaConvidadoDao
    abstract fun emailDao(): EmailDao
    abstract fun anexoDao(): AnexoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun alteracaoDao(): AlteracaoDao
    abstract fun pastaDao(): PastaDao
    abstract fun respostaEmailDao(): RespostaEmailDao
    abstract fun anexoRespostaEmailDao(): AnexoRespostaEmailDao

    abstract fun aiQuestionDao(): AiQuestionDao

    // Padrão Singleton
    companion object {

        private lateinit var instance: InstanceDatabase

        fun getDatabase(context: Context): InstanceDatabase {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        InstanceDatabase::class.java,
                        "LCWEBMAIILAPP_DB"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}