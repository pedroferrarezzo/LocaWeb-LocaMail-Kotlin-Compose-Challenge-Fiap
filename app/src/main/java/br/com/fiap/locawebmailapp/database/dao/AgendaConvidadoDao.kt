package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.AgendaCor
import br.com.fiap.locawebmailapp.model.IdConvidado

@Dao
interface AgendaConvidadoDao {
    @Insert
    fun criarAgendaConvidado(agendaConvidado: AgendaConvidado): Long

    @Query("SELECT id_convidado FROM T_LCW_AGENDA_CONVIDADO WHERE id_agenda = :id_agenda")
    fun listarIdConvidadoPorAgenda(id_agenda: Long): List<IdConvidado>

    @Query("DELETE FROM T_LCW_AGENDA_CONVIDADO WHERE grupo_repeticao = :grupo_repeticao AND id_agenda != :id_agenda")
    fun excluirPorGrupoRepeticaoExcetoIdAgenda(grupo_repeticao: Int, id_agenda: Long): Int

    @Query("DELETE FROM T_LCW_AGENDA_CONVIDADO WHERE id_agenda = :id_agenda")
    fun excluirPorIdAgenda(id_agenda: Long): Int

    @Query("DELETE FROM T_LCW_AGENDA_CONVIDADO WHERE id_agenda = :id_agenda AND id_convidado = :id_convidado")
    fun excluirPorIdAgendaEIdConvidado(id_agenda: Long, id_convidado: Long): Int

}