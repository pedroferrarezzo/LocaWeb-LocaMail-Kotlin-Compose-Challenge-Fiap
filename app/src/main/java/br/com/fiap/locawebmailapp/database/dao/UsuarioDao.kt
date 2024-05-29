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

    @Query("SELECT * FROM T_LCW_USUARIO where id_usuario = :id_usuario")
    fun retornaUsuarioPorId(id_usuario: Long): Usuario


    @Query("SELECT * FROM T_LCW_USUARIO where email != 'dev@locaweb.com.br'")
    fun listarUsuarios(): List<Usuario>

    @Query("SELECT * FROM T_LCW_USUARIO where selected_user = 0 AND email != 'dev@locaweb.com.br'")
    fun listarUsuariosNaoSelecionados(): List<Usuario>

    @Query("SELECT * FROM T_LCW_USUARIO where selected_user = 1 AND email != 'dev@locaweb.com.br'")
    fun listarUsuarioSelecionado(): Usuario

    @Query("UPDATE T_LCW_USUARIO SET selected_user = 0 where selected_user = 1")
    fun desselecionarUsuarioSelecionadoAtual()

    @Query("UPDATE T_LCW_USUARIO SET selected_user = 1 where id_usuario = :id_usuario")
    fun selecionarUsuario(id_usuario: Long)

    @Query("UPDATE T_LCW_USUARIO SET autenticado = :autenticado where id_usuario = :id_usuario")
    fun atualizaAutenticaUsuario(id_usuario: Long, autenticado: Boolean)


    @Query("SELECT * FROM T_LCW_USUARIO where autenticado = 1 AND email != 'dev@locaweb.com.br'")
    fun listarUsuariosAutenticados(): List<Usuario>


}