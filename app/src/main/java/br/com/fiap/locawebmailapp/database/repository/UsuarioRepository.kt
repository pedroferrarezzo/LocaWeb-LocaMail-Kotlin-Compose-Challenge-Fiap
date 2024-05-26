package br.com.fiap.locawebmailapp.database.repository

import android.content.Context
import br.com.fiap.locawebmailapp.database.dao.InstanceDatabase
import br.com.fiap.locawebmailapp.model.Usuario

class UsuarioRepository(context: Context) {
    private val usuarioDao = InstanceDatabase.getDatabase(context).usuarioDao()


    fun criarUsuario(usuario: Usuario): Long {
        return usuarioDao.criarUsuario(usuario)
    }


    fun listarUsuarios(): List<Usuario> {
        return usuarioDao.listarUsuarios()
    }

    fun atualizaAutenticaUsuario(id_usuario: Long, autenticado: Boolean){
        return usuarioDao.atualizaAutenticaUsuario(id_usuario, autenticado)
    }

    fun listarUsuariosAutenticados(): List<Usuario>{
        return usuarioDao.listarUsuariosAutenticados()
    }


    fun retornaUsarioPorEmail(email: String): Usuario {
        return usuarioDao.retornaUsarioPorEmail(email)
    }

    fun retornaUsuarioPorId(id_usuario: Long): Usuario {
        return usuarioDao.retornaUsuarioPorId(id_usuario)
    }


    fun listarUsuarioSelecionado(): Usuario {
        return usuarioDao.listarUsuarioSelecionado()
    }

    fun desselecionarUsuarioSelecionadoAtual() {
        return usuarioDao.desselecionarUsuarioSelecionadoAtual()
    }

    fun selecionarUsuario(id_usuario: Long) {
        return usuarioDao.selecionarUsuario(id_usuario)
    }


    fun listarUsuariosNaoSelecionados(): List<Usuario> {
        return usuarioDao.listarUsuariosNaoSelecionados()
    }


}