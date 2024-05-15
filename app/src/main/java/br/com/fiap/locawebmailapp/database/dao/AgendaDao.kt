package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaCor
import br.com.fiap.locawebmailapp.model.AgendaGrupoRepeticao

@Dao
interface AgendaDao {
    @Insert
    fun criarAgenda(agenda: Agenda): Long

    @Query("SELECT * FROM T_LCW_AGENDA WHERE data = :data")
    fun listarAgendaPorDia(data: String): List<Agenda>

    @Query("SELECT DISTINCT cor FROM T_LCW_AGENDA WHERE data = :data")
    fun listarCorAgendaPorDia(data: String): List<AgendaCor>

    @Query("SELECT DISTINCT grupo_repeticao FROM T_LCW_AGENDA")
    fun listarGrupoRepeticao(): List<AgendaGrupoRepeticao>

    @Query("SELECT * FROM T_LCW_AGENDA WHERE id_agenda = :id_agenda")
    fun listarAgendaPorId(id_agenda: Int): Agenda

    @Query("DELETE FROM T_LCW_AGENDA WHERE grupo_repeticao = :grupo_repeticao AND data != :data")
    fun excluirPorGrupoRepeticaoExcetoData(grupo_repeticao: Int, data: String): Int

    @Query("UPDATE T_LCW_AGENDA SET grupo_repeticao = :grupo_desejado WHERE grupo_repeticao = :grupo_repeticao")
    fun atualizaGrupoRepeticaoPorGrupoRepeticao(grupo_repeticao: Int, grupo_desejado: Int): Int

    @Query("UPDATE T_LCW_AGENDA SET repeticao = :repeticao WHERE grupo_repeticao = :grupo_repeticao")
    fun atualizaOpcaoRepeticaoPorGrupoRepeticao(grupo_repeticao: Int, repeticao: Int): Int

    @Query("UPDATE T_LCW_AGENDA SET repeticao = :repeticao WHERE id_agenda = :id_agenda")
    fun atualizaOpcaoRepeticaoPorIdAgenda(id_agenda: Long, repeticao: Int): Int

    @Query("SELECT seq FROM sqlite_sequence WHERE name=\"T_LCW_AGENDA\"")
    fun retornaValorAtualSeqPrimayKey(): Long

    @Query("DELETE FROM T_LCW_AGENDA WHERE grupo_repeticao = :grupo_repeticao")
    fun excluirPorGrupoRepeticao(grupo_repeticao: Int): Int

    @Update
    fun atualizaAgenda(agenda: Agenda): Int

    @Delete
    fun excluiAgenda(agenda: Agenda): Int
}