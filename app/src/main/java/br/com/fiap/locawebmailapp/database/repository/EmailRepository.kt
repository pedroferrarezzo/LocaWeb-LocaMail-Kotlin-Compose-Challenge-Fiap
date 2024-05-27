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

            val usuario = usuarioRepository.retornaUsarioPorEmail(email.email.remetente)

            val idRemetente = if (usuario != null) usuario.id_usuario else null

            if (listTodosEmails.isNotEmpty() && idRemetente != null) {
                if (email.alteracao.alt_id_usuario != idRemetente && listTodosEmails.last().alteracao.alt_id_email != email.alteracao.alt_id_email) {
                    listTodosEmails.add(email)
                }
            } else if (idRemetente != null) {
                if (email.alteracao.alt_id_usuario != idRemetente) {
                    listTodosEmails.add(email)
                }
            }


        }
        return listTodosEmails
    }

    fun listarEmailsEnviadosPorRemetente(
        remetente: String,
        id_usuario: Long
    ): List<EmailComAlteracao> {
        return emailDao.listarEmailsEnviadosPorRemetente(remetente, id_usuario)
    }


    fun listarEmailsPorDestinatario(
        destinatario: String,
        id_usuario: Long,
        respostaEmailRepository: RespostaEmailRepository
    ): List<EmailComAlteracao> {
        val emailListDb = emailDao.listarEmailsPorDestinatario(id_usuario)

        return emailListDb.filter { email ->
            val respostaEmailList =
                respostaEmailRepository.listarRespostasEmailPorIdEmail(email.email.id_email)

            stringParaLista(email.email.destinatario).contains(destinatario) ||
                    stringParaLista(email.email.cc).contains(destinatario) ||
                    stringParaLista(email.email.cco).contains(destinatario) ||
                    respostaEmailList.any {respostaEmail ->
                        stringParaLista(respostaEmail.destinatario).contains(destinatario) ||
                                stringParaLista(respostaEmail.cc).contains(destinatario) ||
                                stringParaLista(respostaEmail.cco).contains(destinatario)
                    }
        }
    }

    fun listarEmailPorId(id_email: Long): Email {
        return emailDao.listarEmailPorId(id_email)
    }

    fun listarEmailsEditaveisPorRemetente(remetente: String): List<Email> {
        return emailDao.listarEmailsEditaveisPorRemetente(remetente)
    }


    fun listarEmailsPorPastaEIdUsuario(id_usuario: Long, id_pasta: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsPorPastaEIdUsuario(id_usuario, id_pasta)
    }

    fun listarEmailsImportantesPorIdUsuario(id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsImportantesPorIdUsuario(id_usuario)
    }

    fun listarEmailsAi(id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsAi(id_usuario)
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

    fun listarEmailsSociaisPorIdUsuario(id_usuario: Long): List<EmailComAlteracao> {
        return emailDao.listarEmailsSociaisPorIdUsuario(id_usuario)
    }

}