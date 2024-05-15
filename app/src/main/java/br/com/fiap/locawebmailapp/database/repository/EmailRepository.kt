package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import android.util.Log
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.utils.stringParaLista

class EmailRepository(context: Context) {
    private val emailDao = InstanceDatabase.getDatabase(context).emailDao()

    fun criarEmail(email: Email): Long {
        return emailDao.criarEmail(email)
    }
    fun excluirEmail(email: Email) {
        return emailDao.excluirEmail(email)
    }
    fun atualizarEmail(email: Email) {
        return emailDao.atualizarEmail(email)
    }

    fun listarTodosEmails(usuarioRepository: UsuarioRepository): List<EmailComAlteracao> {
        val listTodosEmails = arrayListOf<EmailComAlteracao>()

        for (email in emailDao.listarTodosEmails()) {

            val idRemetente = usuarioRepository.retornaUsarioPorEmail(email.email.remetente).id_usuario

            if (listTodosEmails.isNotEmpty()) {
                if (email.alteracao.alt_id_usuario != idRemetente && listTodosEmails.last().alteracao.alt_id_email != email.alteracao.alt_id_email) {
                    listTodosEmails.add(email)
                }
            }

            else {
                if (email.alteracao.alt_id_usuario != idRemetente) {
                    listTodosEmails.add(email)
                }
            }


        }
        return listTodosEmails
    }

    fun listarEmailsEnviadosPorRemetente(remetente: String, id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsEnviadosPorRemetente(remetente, id_usuario)
    }


    fun listarEmailsPorDestinatario(destinatario: String, id_usuario: Long): List<EmailComAlteracao> {
        val emailListDb = emailDao.listarEmailsPorDestinatario(id_usuario)
        return emailListDb.filter {
            stringParaLista(it.email.destinatario).contains(destinatario) ||
                    stringParaLista(it.email.cc).contains(destinatario) ||
                    stringParaLista(it.email.cco).contains(destinatario)
        }
    }
    fun listarEmailPorId(id_email: Long): Email {
        return emailDao.listarEmailPorId(id_email)
    }
    fun listarEmailsEditaveisPorRemetente(remetente: String): List<Email> {
        return emailDao.listarEmailsEditaveisPorRemetente(remetente)
    }

    fun listarEmailsImportantesPorIdUsuario(id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsImportantesPorIdUsuario(id_usuario)
    }
    fun listarEmailsArquivadosPorIdUsuario(id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsArquivadosPorIdUsuario(id_usuario)
    }
    fun listarEmailsLixeiraPorIdUsuario(id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsLixeiraPorIdUsuario(id_usuario)
    }
    fun listarEmailsSpamPorIdUsuario(id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsSpamPorIdUsuario(id_usuario)
    }

}