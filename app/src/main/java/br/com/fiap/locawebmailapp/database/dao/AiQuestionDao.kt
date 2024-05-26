package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.ai.AiQuestion

@Dao
interface AiQuestionDao {
    @Insert
    fun criarPergunta(aiQuestion: AiQuestion): Long

    @Query("SELECT * FROM T_LCW_AI_QUESTION WHERE id_question = :id_question AND id_email = :id_email")
    fun obterPergunta(id_question: Long, id_email: Long): AiQuestion

}