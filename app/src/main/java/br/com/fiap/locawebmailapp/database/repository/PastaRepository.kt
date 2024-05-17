package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.Usuario

class PastaRepository(context: Context) {
    private val pastaDao = InstanceDatabase.getDatabase(context).pastaDao()

    fun criarPasta(pasta: Pasta): Long {
        return pastaDao.criarPasta(pasta)
    }

    fun listarPastasPorIdUsuario(id_usuario: Long): List<Pasta> {
        return pastaDao.listarPastasPorIdUsuario(id_usuario)
    }

    fun excluirPasta(pasta: Pasta) {
        return pastaDao.excluirPasta(pasta = pasta)
    }


}