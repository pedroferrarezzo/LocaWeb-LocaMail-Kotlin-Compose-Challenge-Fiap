package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.locawebmailapp.model.RespostaEmail

@Dao
interface RespostaEmailDao {
    @Insert
    fun criarRespostaEmail(respostaEmail: RespostaEmail): Long
    @Update
    fun atualizarRespostaEmail(respostaEmail: RespostaEmail)
    @Delete
    fun excluirRespostaEmail(respostaEmail: RespostaEmail)

    @Query("SELECT * FROM T_LCW_RESPOSTA_EMAIL  WHERE id_email = :id_email")
    fun listarRespostasEmailPorIdEmail(id_email: Long): List<RespostaEmail>

    @Query("SELECT * FROM T_LCW_RESPOSTA_EMAIL  WHERE id_resposta_email = :id_resposta_email")
    fun listarRespostaEmailPorIdRespostaEmail(id_resposta_email: Long): RespostaEmail

    @Query("DELETE FROM T_LCW_RESPOSTA_EMAIL WHERE id_email = :id_email")
    fun excluirRespostaEmailPorIdEmail(id_email: Long)
}