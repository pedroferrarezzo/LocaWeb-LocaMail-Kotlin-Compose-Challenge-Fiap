package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.AgendaCor
import br.com.fiap.locawebmailapp.model.AgendaGrupoRepeticao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.IdConvidado

class AgendaConvidadoRepository(context: Context) {
    private val agendaConvidadoDao = InstanceDatabase.getDatabase(context).agendaConvidadoDao()

    fun criaAgendaConvidado(agendaConvidado: AgendaConvidado): Long {
        return agendaConvidadoDao.criarAgendaConvidado(agendaConvidado)
    }

    fun listarIdConvidadoPorAgenda(id_agenda: Long): List<IdConvidado> {
        return agendaConvidadoDao.listarIdConvidadoPorAgenda(id_agenda)
    }

    fun excluirPorGrupoRepeticaoExcetoIdAgenda(grupo_repeticao: Int, id_agenda: Long): Int {
        return agendaConvidadoDao.excluirPorGrupoRepeticaoExcetoIdAgenda(grupo_repeticao, id_agenda)
    }

    fun excluirPorIdAgenda(id_agenda: Long): Int {
        return agendaConvidadoDao.excluirPorIdAgenda(id_agenda)
    }

    fun excluirPorIdAgendaEIdConvidado(id_agenda: Long, id_convidado: Long): Int {
        return agendaConvidadoDao.excluirPorIdAgendaEIdConvidado(
            id_agenda = id_agenda,
            id_convidado = id_convidado
        )
    }

}