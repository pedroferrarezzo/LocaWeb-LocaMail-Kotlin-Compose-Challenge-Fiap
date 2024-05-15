package br.com.fiap.locawebmailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.locawebmailapp.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    fun criarUsuario(usuario: Usuario): Long


    @Query("SELECT * FROM T_LCW_USUARIO where email = :email")
    fun retornaUsarioPorEmail(email: String): Usuario


    @Query("SELECT * FROM T_LCW_USUARIO")
    fun listarUsuarios(): List<Usuario>

    @Query("SELECT * FROM T_LCW_USUARIO where selected_user = 0")
    fun listarUsuariosNaoSelecionados(): List<Usuario>

    @Query("SELECT * FROM T_LCW_USUARIO where selected_user = 1")
    fun listarUsuarioSelecionado(): Usuario

    @Query("UPDATE T_LCW_USUARIO SET selected_user = 0 where selected_user = 1")
    fun desselecionarUsuarioSelecionadoAtual()

    @Query("UPDATE T_LCW_USUARIO SET selected_user = 1 where id_usuario = :id_usuario")
    fun selecionarUsuario(id_usuario: Long)


}