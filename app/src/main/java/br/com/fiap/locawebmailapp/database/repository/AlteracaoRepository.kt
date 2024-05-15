package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Alteracao

class AlteracaoRepository(context: Context) {
    private val alteracaoDao = InstanceDatabase.getDatabase(context).alteracaoDao()


    fun criarAlteracao(alteracao: Alteracao) {
        return alteracaoDao.criarAlteracao(alteracao)
    }

    fun atualizarImportantePorIdEmail(importante: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarImportantePorIdEmailEIdUsuario(importante, id_email, id_usuario)
    }

    fun listarAlteracaoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Alteracao {
        return alteracaoDao.listarAlteracaoPorIdEmailEIdUsuario(id_email, id_usuario)
    }

    fun verificarLidoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean {
        return alteracaoDao.verificarLidoPorIdEmailEIdUsuario(id_email, id_usuario)
    }

    fun listarAlteracaoPorIdEmail(id_email: Long): Alteracao {
        return alteracaoDao.listarAlteracaoPorIdEmail(id_email)
    }

    fun atualizarArquivadoPorIdEmail(arquivado: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarArquivadoPorIdEmailEIdUsuario(arquivado, id_email, id_usuario)
    }

    fun atualizarLidoPorIdEmail(lido: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarLidoPorIdEmailEIdUsuario(lido, id_email, id_usuario)
    }
    fun atualizarExcluidoPorIdEmail(excluido: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarExcluidoPorIdEmailEIdUsuario(excluido, id_email, id_usuario)
    }

    fun atualizarSpamPorIdEmail(spam: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarSpamPorIdEmailEIdUsuario(spam, id_email, id_usuario)
    }


    fun excluiAlteracaoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long) {
        return alteracaoDao.excluiAlteracaoPorIdEmailEIdUsuario(id_email, id_usuario)
    }



    fun verificarImportantePorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean {
        return alteracaoDao.verificarImportantePorIdEmailEIdUsuario(id_email, id_usuario)
    }

    fun verificarExcluidoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean {
        return alteracaoDao.verificarExcluidoPorIdEmailEIdUsuario(id_email, id_usuario)
    }

    fun verificarArquivadoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean {
        return alteracaoDao.verificarArquivadoPorIdEmailEIdUsuario(id_email, id_usuario)
    }

    fun verificarSpamPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean {
        return alteracaoDao.verificarSpamPorIdEmailEIdUsuario(id_email, id_usuario)
    }






}