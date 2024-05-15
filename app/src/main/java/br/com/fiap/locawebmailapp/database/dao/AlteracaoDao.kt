package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Email

@Dao
interface AlteracaoDao {

    @Insert
    fun criarAlteracao(alteracao: Alteracao)



    @Query("SELECT * FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun listarAlteracaoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Alteracao

    @Query("SELECT * FROM T_LCW_ALTERACAO where alt_id_email = :id_email")
    fun listarAlteracaoPorIdEmail(id_email: Long): Alteracao

    @Query("SELECT importante FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun verificarImportantePorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean

    @Query("SELECT lido FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun verificarLidoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean

    @Query("SELECT arquivado FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun verificarArquivadoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean

    @Query("SELECT excluido FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun verificarExcluidoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean

    @Query("SELECT spam FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun verificarSpamPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long): Boolean


    @Query("UPDATE T_LCW_ALTERACAO SET importante = :importante where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun atualizarImportantePorIdEmailEIdUsuario(importante: Boolean, id_email: Long, id_usuario: Long)

    @Query("UPDATE T_LCW_ALTERACAO SET arquivado = :arquivado where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun atualizarArquivadoPorIdEmailEIdUsuario(arquivado: Boolean, id_email: Long, id_usuario: Long)

    @Query("UPDATE T_LCW_ALTERACAO SET spam = :spam where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun atualizarSpamPorIdEmailEIdUsuario(spam: Boolean, id_email: Long, id_usuario: Long)

    @Query("UPDATE T_LCW_ALTERACAO SET excluido = :excluido where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun atualizarExcluidoPorIdEmailEIdUsuario(excluido: Boolean, id_email: Long, id_usuario: Long)

    @Query("UPDATE T_LCW_ALTERACAO SET lido = :lido where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun atualizarLidoPorIdEmailEIdUsuario(lido: Boolean, id_email: Long, id_usuario: Long)


    @Query("DELETE FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario")
    fun excluiAlteracaoPorIdEmailEIdUsuario(id_email: Long, id_usuario: Long)
}