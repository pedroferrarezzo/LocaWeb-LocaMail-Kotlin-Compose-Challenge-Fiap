package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.Anexo

@Dao
interface AnexoDao {
    @Insert
    fun criarAnexo(anexo: Anexo): Long

    @Query("SELECT id_email FROM T_LCW_ANEXO")
    fun listarAnexosIdEmail(): List<Long>

    @Query("SELECT id_email FROM T_LCW_ANEXO where id_email = :id_email LIMIT 1")
    fun verificarAnexoExistentePorIdEmail(id_email: Long): Long

    @Query("SELECT anexo FROM T_LCW_ANEXO where id_email = :id_email")
    fun listarAnexosArrayBytePorIdEmail(id_email: Long): List<ByteArray>

    @Query("DELETE FROM T_LCW_ANEXO where id_email = :id_email")
    fun excluirAnexoPorIdEmail(id_email: Long)


}