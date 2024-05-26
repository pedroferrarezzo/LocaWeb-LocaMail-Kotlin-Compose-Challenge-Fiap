package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.ai.AiQuestion

class AiQuestionRepository(context: Context) {
    private val aiQuestionDao = InstanceDatabase.getDatabase(context).aiQuestionDao()

    fun criarPergunta(aiQuestion: AiQuestion): Long{
        return aiQuestionDao.criarPergunta(aiQuestion)
    }

    fun listarPergunta(id_question: Long, id_email: Long): AiQuestion{
        return aiQuestionDao.obterPergunta(id_question, id_email)
    }




}