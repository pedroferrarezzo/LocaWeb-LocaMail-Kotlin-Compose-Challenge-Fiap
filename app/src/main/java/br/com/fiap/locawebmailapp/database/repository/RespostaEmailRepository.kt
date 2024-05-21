package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.RespostaEmail


class RespostaEmailRepository(context: Context) {

    private val respostaEmailDao =
        InstanceDatabase.getDatabase(context = context).respostaEmailDao()

    fun criarRespostaEmail(respostaEmail: RespostaEmail): Long {

        return respostaEmailDao.criarRespostaEmail(respostaEmail)

    }

    fun listarRespostaEmailPorIdRespostaEmail(id_resposta_email: Long): RespostaEmail {
        return respostaEmailDao.listarRespostaEmailPorIdRespostaEmail(id_resposta_email)
    }

    fun atualizarRespostaEmail(respostaEmail: RespostaEmail) {

        return respostaEmailDao.atualizarRespostaEmail(respostaEmail)

    }

    fun excluirRespostaEmail(respostaEmail: RespostaEmail) {

        return respostaEmailDao.excluirRespostaEmail(respostaEmail)

    }

    fun listarRespostasEmailPorIdEmail(id_email: Long): List<RespostaEmail> {

        return respostaEmailDao.listarRespostasEmailPorIdEmail(id_email)

    }

    fun excluirRespostaEmailPorIdEmail(id_email: Long) {
        return respostaEmailDao.excluirRespostaEmailPorIdEmail(id_email)
    }
}