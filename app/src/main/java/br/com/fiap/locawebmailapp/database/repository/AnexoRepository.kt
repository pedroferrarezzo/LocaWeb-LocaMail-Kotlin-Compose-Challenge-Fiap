package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Anexo

class AnexoRepository(context: Context) {
    private val anexoDao = InstanceDatabase.getDatabase(context).anexoDao()

    fun criarAnexo(anexo: Anexo): Long {
        return anexoDao.criarAnexo(anexo)
    }

    fun listarAnexosIdEmail(): List<Long> {
        return anexoDao.listarAnexosIdEmail()
    }

    fun verificarAnexoExistentePorIdEmail(id_email: Long): Long {
        return anexoDao.verificarAnexoExistentePorIdEmail(id_email)
    }

    fun listarAnexosArraybytePorIdEmail(id_email: Long): List<ByteArray> {
        return anexoDao.listarAnexosArrayBytePorIdEmail(id_email)
    }

    fun excluirAnexoPorIdEmail(id_email: Long) {
        return anexoDao.excluirAnexoPorIdEmail(id_email)
    }


}