package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.AnexoRespostaEmail

@Dao
interface AnexoRespostaEmailDao {
    @Insert
    fun criarAnexo(anexo: AnexoRespostaEmail): Long

    @Query("SELECT id_resposta_email FROM T_LCW_ANEXO_RESPOSTA_EMAIL")
    fun listarAnexosIdRespostaEmail(): List<Long>

    @Query("SELECT id_resposta_email FROM T_LCW_ANEXO_RESPOSTA_EMAIL where id_resposta_email = :id_resposta_email LIMIT 1")
    fun verificarAnexoExistentePorIdEmail(id_resposta_email: Long): Long

    @Query("SELECT anexo FROM T_LCW_ANEXO_RESPOSTA_EMAIL where id_resposta_email = :id_resposta_email")
    fun listarAnexosArrayBytePorIdRespostaEmail(id_resposta_email: Long): List<ByteArray>

    @Query("DELETE FROM T_LCW_ANEXO_RESPOSTA_EMAIL where id_resposta_email = :id_resposta_email")
    fun excluirAnexoPorIdRespostaEmail(id_resposta_email: Long)

}