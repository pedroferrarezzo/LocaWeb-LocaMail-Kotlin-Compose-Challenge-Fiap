package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.Convidado

@Dao
interface ConvidadoDao {

    @Query("SELECT * from T_LCW_CONVIDADO")
    fun listarConvidado(): List<Convidado>

    @Query("SELECT * from T_LCW_CONVIDADO where id_convidado = :id_convidado")
    fun listarConvidadoPorId(id_convidado: Long): Convidado

    @Query("SELECT email FROM T_LCW_CONVIDADO where email = :email LIMIT 1")
    fun verificarConvidadoExiste(email: String): String

    @Insert
    fun criarConvidado(convidado: Convidado)
}