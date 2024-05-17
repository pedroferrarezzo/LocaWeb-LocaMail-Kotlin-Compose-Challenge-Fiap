package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.AnexoRespostaEmail

class AnexoRespostaEmailRepository(context: Context) {
    private val anexoRespostaEmailDao = InstanceDatabase.getDatabase(context).anexoRespostaEmailDao()


    fun criarAnexo(anexo: AnexoRespostaEmail): Long {
        return anexoRespostaEmailDao.criarAnexo(anexo)
    }


    fun listarAnexosIdRespostaEmail(): List<Long> {
        return anexoRespostaEmailDao.listarAnexosIdRespostaEmail()
    }


    fun verificarAnexoExistentePorIdRespostaEmail(id_resposta_email: Long): Long {
        return anexoRespostaEmailDao.verificarAnexoExistentePorIdEmail(id_resposta_email)
    }


    fun listarAnexosArrayBytePorIdRespostaEmail(id_resposta_email: Long): List<ByteArray> {
        return anexoRespostaEmailDao.listarAnexosArrayBytePorIdRespostaEmail(id_resposta_email)

    }


    fun excluirAnexoPorIdRespostaEmail(id_resposta_email: Long) {
        return anexoRespostaEmailDao.excluirAnexoPorIdRespostaEmail(id_resposta_email)
    }
}