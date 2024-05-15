package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.AgendaCor
import br.com.fiap.locawebmailapp.model.AgendaGrupoRepeticao
import br.com.fiap.locawebmailapp.model.Convidado

class ConvidadoRepository(context: Context) {
    private val convidadoDao = InstanceDatabase.getDatabase(context).convidadoDao()

    fun listarConvidado(): List<Convidado> {
        return convidadoDao.listarConvidado()
    }

    fun listarConvidadoPorId(id_convidado: Long): Convidado {
        return convidadoDao.listarConvidadoPorId(id_convidado)
    }

    fun verificarConvidadoExiste(email: String): String {
        return convidadoDao.verificarConvidadoExiste(email)
    }

    fun criarConvidado(convidado: Convidado) {
        return convidadoDao.criarConvidado(convidado)
    }

}