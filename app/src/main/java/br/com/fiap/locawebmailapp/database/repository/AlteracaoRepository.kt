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

    fun listarAlteracaoPorIdEmail(id_email: Long): List<Alteracao> {
        return alteracaoDao.listarAlteracaoPorIdEmail(id_email)
    }

    fun listarAltIdUsuarioPorIdEmail(id_email: Long): List<Long> {
        return alteracaoDao.listarAltIdUsuarioPorIdEmail(id_email)
    }

    fun atualizarArquivadoPorIdEmailEIdusuario(arquivado: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarArquivadoPorIdEmailEIdUsuario(arquivado, id_email, id_usuario)
    }

    fun atualizarLidoPorIdEmailEIdusuario(lido: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarLidoPorIdEmailEIdUsuario(lido, id_email, id_usuario)
    }
    fun atualizarExcluidoPorIdEmailEIdusuario(excluido: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarExcluidoPorIdEmailEIdUsuario(excluido, id_email, id_usuario)
    }

    fun atualizarSpamPorIdEmailEIdusuario(spam: Boolean, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarSpamPorIdEmailEIdUsuario(spam, id_email, id_usuario)
    }

    fun listarAlteracaoPorIdUsuarioEIdPasta(id_usuario: Long, id_pasta: Long): List<Alteracao> {
        return alteracaoDao.listarAlteracaoPorIdUsuarioEIdPasta(id_usuario, id_pasta)
    }



    fun atualizarPastaPorIdEmailEIdUsuario(pasta: Long?, id_email: Long, id_usuario: Long) {
        return alteracaoDao.atualizarPastaPorIdEmailEIdUsuario(pasta, id_email, id_usuario)
    }

    fun atualizarPastaPorIdAlteracao(pasta: Long?, id_alteracao: Long) {
        return alteracaoDao.atualizarPastaPorIdAlteracao(pasta, id_alteracao)
    }


    fun excluiAlteracaoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long) {
        return alteracaoDao.excluiAlteracaoPorIdEmailEIdUsuario(id_email, id_usuario)
    }



    fun verificarImportantePorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean {
        return alteracaoDao.verificarImportantePorIdEmailEIdUsuario(id_email, id_usuario)
    }

    fun verificarPastaPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Long {
        return alteracaoDao.verificarPastaPorIdEmailEIdUsuario(id_email, id_usuario)
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