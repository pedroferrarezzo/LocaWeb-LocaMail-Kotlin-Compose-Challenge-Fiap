package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.MutableState
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Email

fun atualizarIsImportantParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    usuarioRepository: UsuarioRepository,
    alteracaoRepository: AlteracaoRepository,
    isImportant: MutableState<Boolean>,
    email: Email
) {
    for (destinatario in todosDestinatarios) {

        if (destinatario.isNotBlank()) {

            val usuario =
                usuarioRepository.retornaUsarioPorEmail(
                    destinatario
                )

            val idDestinatario =
                if (usuario != null) usuario.id_usuario else null

            if (idDestinatario != null) {
                val alteracao =
                    alteracaoRepository.listarAlteracaoPorIdEmailEIdUsuario(
                        email.id_email,
                        idDestinatario
                    )

                if (alteracao != null) {

                    isImportant.value =
                        alteracaoRepository.verificarImportantePorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario
                        )
                    if (!isImportant.value) {
                        break
                    }
                }
            }
        }
    }
}

fun atualizarisArchiveParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    usuarioRepository: UsuarioRepository,
    alteracaoRepository: AlteracaoRepository,
    isArchive: MutableState<Boolean>,
    email: Email
) {
    for (destinatario in todosDestinatarios) {
        if (destinatario.isNotBlank()) {
            val usuario =
                usuarioRepository.retornaUsarioPorEmail(
                    destinatario
                )

            val idDestinatario =
                if (usuario != null) usuario.id_usuario else null

            if (idDestinatario != null) {
                val alteracao =
                    alteracaoRepository.listarAlteracaoPorIdEmailEIdUsuario(
                        email.id_email,
                        idDestinatario
                    )

                if (alteracao != null) {

                    isArchive.value =
                        alteracaoRepository.verificarArquivadoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario
                        )

                    if (!isArchive.value) {
                        break
                    }
                }
            }
        }
    }
}


fun atualizarisSpamParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    usuarioRepository: UsuarioRepository,
    alteracaoRepository: AlteracaoRepository,
    isSpam: MutableState<Boolean>,
    email: Email
) {
    for (destinatario in todosDestinatarios) {

        if (destinatario.isNotBlank()) {
            val usuario =
                usuarioRepository.retornaUsarioPorEmail(
                    destinatario
                )

            val idDestinatario =
                if (usuario != null) usuario.id_usuario else null

            if (idDestinatario != null) {
                val alteracao =
                    alteracaoRepository.listarAlteracaoPorIdEmailEIdUsuario(
                        email.id_email,
                        idDestinatario
                    )

                if (alteracao != null) {
                    isSpam.value = alteracaoRepository.verificarSpamPorIdEmailEIdUsuario(
                        email.id_email,
                        idDestinatario
                    )

                    if (!isSpam.value) {
                        break
                    }
                }
            }
        }

    }
}



fun atualizarisExcluidoParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    usuarioRepository: UsuarioRepository,
    alteracaoRepository: AlteracaoRepository,
    isExcluido: MutableState<Boolean>,
    email: Email
) {
    for (destinatario in todosDestinatarios) {
        if (destinatario.isNotBlank()) {
            val usuario =
                usuarioRepository.retornaUsarioPorEmail(
                    destinatario
                )

            val idDestinatario =
                if (usuario != null) usuario.id_usuario else null

            if (idDestinatario != null) {

                val alteracao =
                    alteracaoRepository.listarAlteracaoPorIdEmailEIdUsuario(
                        email.id_email,
                        idDestinatario
                    )

                if (alteracao != null) {
                    isExcluido.value =
                        alteracaoRepository.verificarExcluidoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario
                        )

                    if (!isExcluido.value) {
                        break
                    }
                }
            }
        }
    }
}


fun atualizarisReadParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    usuarioRepository: UsuarioRepository,
    alteracaoRepository: AlteracaoRepository,
    isRead: MutableState<Boolean>,
    email: Email
) {
    for (destinatario in todosDestinatarios) {

        if (destinatario.isNotBlank()) {
            val usuario =
                usuarioRepository.retornaUsarioPorEmail(destinatario)

            val idDestinatario =
                if (usuario != null) usuario.id_usuario else null

            if (idDestinatario != null) {
                val alteracao =
                    alteracaoRepository.listarAlteracaoPorIdEmailEIdUsuario(
                        email.id_email,
                        idDestinatario
                    )

                if (alteracao != null) {
                    isRead.value =
                        alteracaoRepository.verificarLidoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario
                        )

                    if (!isRead.value) {
                        break
                    }
                }
            }
        }
    }
}