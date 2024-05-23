package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaCor
import br.com.fiap.locawebmailapp.model.AgendaGrupoRepeticao

class AgendaRepository(context: Context) {
    private val agendaDao = InstanceDatabase.getDatabase(context).agendaDao()

    fun criarAgenda(agenda: Agenda): Long {
        return agendaDao.criarAgenda(agenda)
    }

    fun listarAgendaPorDia(data: String, id_usuario: Long): List<Agenda> {
        return agendaDao.listarAgendaPorDia(data, id_usuario)
    }

    fun listarCorAgendaPorDia(data: String, id_usuario: Long): List<AgendaCor> {
        return agendaDao.listarCorAgendaPorDia(data, id_usuario)
    }

    fun listarGrupoRepeticao(): List<AgendaGrupoRepeticao> {
        return agendaDao.listarGrupoRepeticao()
    }

    fun listarAgendaPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): List<Agenda> {
        return agendaDao.listarAgendaPorIdEmailEIdUsuario(id_email, id_usuario)
    }

    fun listarAgendaPorId(id_agenda: Int): Agenda {
        return agendaDao.listarAgendaPorId(id_agenda)
    }

    fun atualizaAgenda(agenda: Agenda): Int {
        return agendaDao.atualizaAgenda(agenda)
    }

    fun atualizaVisivelPorIdAgenda(id_agenda: Long, visivel: Boolean): Int {
        return agendaDao.atualizaVisivelPorIdAgenda(id_agenda, visivel)
    }

    fun excluiAgenda(agenda: Agenda): Int {
        return agendaDao.excluiAgenda(agenda)
    }

    fun excluirPorGrupoRepeticaoExcetoData(grupo_repeticao: Int, data: String): Int {
        return agendaDao.excluirPorGrupoRepeticaoExcetoData(grupo_repeticao, data)
    }

    fun atualizaGrupoRepeticaoPorGrupoRepeticao(grupo_repeticao: Int, grupo_desejado: Int): Int {
        return agendaDao.atualizaGrupoRepeticaoPorGrupoRepeticao(grupo_repeticao, grupo_desejado)
    }

    fun atualizaOpcaoRepeticaoPorGrupoRepeticao(grupo_repeticao: Int, repeticao: Int): Int {
        return agendaDao.atualizaOpcaoRepeticaoPorGrupoRepeticao(grupo_repeticao, repeticao)
    }

    fun atualizaOpcaoRepeticaoPorIdAgenda(id_agenda: Long, repeticao: Int): Int {
        return agendaDao.atualizaOpcaoRepeticaoPorIdAgenda(id_agenda, repeticao)
    }

    fun retornaValorAtualSeqPrimayKey(): Long {
        return agendaDao.retornaValorAtualSeqPrimayKey()
    }

    fun excluirPorGrupoRepeticao(grupo_repeticao: Int): Int {
        return agendaDao.excluirPorGrupoRepeticao(grupo_repeticao)
    }
}