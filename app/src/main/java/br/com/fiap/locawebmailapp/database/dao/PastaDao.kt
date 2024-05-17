package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.Usuario

@Dao
interface PastaDao {
    @Insert
    fun criarPasta(pasta: Pasta): Long


    @Delete
    fun excluirPasta(pasta: Pasta)

    @Query("SELECT * FROM T_LCW_PASTA where id_usuario = :id_usuario")
    fun listarPastasPorIdUsuario(id_usuario: Long): List<Pasta>

}