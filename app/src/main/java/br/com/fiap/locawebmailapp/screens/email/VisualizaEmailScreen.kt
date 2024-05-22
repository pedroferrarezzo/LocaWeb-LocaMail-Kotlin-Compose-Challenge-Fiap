package br.com.fiap.locawebmailapp.screens.email

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.ColumnEmailDetails
import br.com.fiap.locawebmailapp.components.email.RowTopOptionsViewEmail
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.utils.atualizarIsImportantParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.atualizarTodosDestinatarios
import br.com.fiap.locawebmailapp.utils.atualizarisArchiveParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.atualizarisExcluidoParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.atualizarisSpamParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizaEmailScreen(
    timeState: TimePickerState = rememberTimePickerState(),
    navController: NavController,
    idEmail: Long,
    isTodasContasScreen: Boolean,
) {

    val context = LocalContext.current

    val emailRepository = EmailRepository(context)
    val anexoRepository = AnexoRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val respostaEmailRepository = RespostaEmailRepository(context)
    val anexoRespostaEmailRepository = AnexoRespostaEmailRepository(context)


    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val email = emailRepository.listarEmailPorId(idEmail)

    val alteracao = alteracaoRepository.listarAlteracaoPorIdEmailEIdUsuario(
        idEmail,
        usuarioSelecionado.value.id_usuario
    )

    Log.i("TEST3", alteracao.alt_id_usuario.toString())

    val todosDestinatarios = arrayListOf<String>()


    val respostasEmailList = respostaEmailRepository.listarRespostasEmailPorIdEmail(idEmail)
    val respostasEmailStateList = respostasEmailList.toMutableStateList()


    val anexoArrayByteList = anexoRepository.listarAnexosArraybytePorIdEmail(idEmail);
    val anexoBitMapList = anexoArrayByteList.map {
        byteArrayToBitmap(it)
    }


    val isImportant = remember {
        if (alteracao != null) mutableStateOf(alteracao.importante) else mutableStateOf(false)
    }

    val isArchive = remember {
        if (alteracao != null) mutableStateOf(alteracao.arquivado) else mutableStateOf(false)
    }

    val isSpam = remember {
        if (alteracao != null) mutableStateOf(alteracao.spam) else mutableStateOf(false)
    }

    val isExcluido = remember {
        if (alteracao != null) mutableStateOf(alteracao.excluido) else mutableStateOf(false)
    }

    if (isTodasContasScreen) {
        atualizarTodosDestinatarios(
            todosDestinatarios,
            email,
            respostasEmailList
        )

        atualizarIsImportantParaUsuariosRelacionados(
            todosDestinatarios,
            usuarioRepository,
            alteracaoRepository,
            isImportant,
            email
        )

        atualizarisArchiveParaUsuariosRelacionados(
            todosDestinatarios,
            usuarioRepository,
            alteracaoRepository,
            isArchive,
            email
        )


        atualizarisSpamParaUsuariosRelacionados(
            todosDestinatarios,
            usuarioRepository,
            alteracaoRepository,
            isSpam,
            email
        )

        atualizarisExcluidoParaUsuariosRelacionados(
            todosDestinatarios,
            usuarioRepository,
            alteracaoRepository,
            isExcluido,
            email
        )
    }

    val toastMessageMailDeleted = stringResource(id = R.string.toast_mail_delete)
    val toastMessageDraftMailDeleted = stringResource(id = R.string.toast_maildraftresp_delete)
    val toastMessageMailMovedToTrashBin = stringResource(id = R.string.toast_mail_moved_trash_bin)
    val toastMessageMailMovedToTrash = stringResource(id = R.string.toast_mail_moved_trash)



    if (email != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            RowTopOptionsViewEmail(
                onClickBack = {
                    navController.popBackStack()
                },
                onClickDelete = {
                    if (isExcluido.value) {

                        alteracaoRepository.excluiAlteracaoPorIdEmailEIdUsuario(
                            email.id_email,
                            usuarioSelecionado.value.id_usuario
                        )

                        val alteracaoList =
                            alteracaoRepository.listarAlteracaoPorIdEmail(email.id_email)

                        if (alteracaoList.isEmpty()) {
                            anexoRepository.excluirAnexoPorIdEmail(email.id_email)

                            for (respostaEmail in respostaEmailRepository.listarRespostasEmailPorIdEmail(
                                email.id_email
                            )) {
                                anexoRespostaEmailRepository.excluirAnexoPorIdRespostaEmail(
                                    respostaEmail.id_resposta_email
                                )
                            }

                            respostaEmailRepository.excluirRespostaEmailPorIdEmail(email.id_email)


                            emailRepository.excluirEmail(email = email)
                        }

                        Toast.makeText(context, toastMessageMailDeleted, Toast.LENGTH_LONG)
                            .show()

                        navController.popBackStack()

                    } else {
                        if (isTodasContasScreen) {
                            for (destinatario in todosDestinatarios) {
                                if (destinatario.isNotBlank()) {
                                    val usuario =
                                        usuarioRepository.retornaUsarioPorEmail(
                                            destinatario
                                        )

                                    val idDestinatario =
                                        if (usuario != null) usuario.id_usuario else null

                                    if (idDestinatario != null) {
                                        alteracaoRepository.atualizarExcluidoPorIdEmailEIdusuario(
                                            true,
                                            email.id_email,
                                            idDestinatario
                                        )
                                    }
                                }
                            }
                            Toast.makeText(
                                context,
                                toastMessageMailMovedToTrashBin,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            alteracaoRepository.atualizarExcluidoPorIdEmailEIdusuario(
                                true,
                                email.id_email,
                                usuarioSelecionado.value.id_usuario
                            )

                            Toast.makeText(
                                context,
                                toastMessageMailMovedToTrash,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        navController.popBackStack()
                    }
                },
                isExcluido = isExcluido,
                isTodasContasScreen = isTodasContasScreen,
                onClickReply = { navController.navigate("criarespostaemailscreen/${idEmail}") },
                onClickSpam = {
                    if (isTodasContasScreen) {
                        isSpam.value = !isSpam.value
                        for (destinatario in todosDestinatarios) {
                            if (destinatario.isNotBlank()) {
                                val usuario =
                                    usuarioRepository.retornaUsarioPorEmail(
                                        destinatario
                                    )

                                val idDestinatario =
                                    if (usuario != null) usuario.id_usuario else null

                                if (idDestinatario != null) {
                                    alteracaoRepository.atualizarSpamPorIdEmailEIdusuario(
                                        isSpam.value,
                                        email.id_email,
                                        idDestinatario
                                    )
                                }
                            }
                        }
                    } else {
                        isSpam.value = !isSpam.value
                        alteracaoRepository.atualizarSpamPorIdEmailEIdusuario(
                            isSpam.value,
                            email.id_email,
                            usuarioSelecionado.value.id_usuario
                        )
                    }
                },
                onClickFavorite = {
                    if (isTodasContasScreen) {
                        isImportant.value = !isImportant.value
                        for (destinatario in todosDestinatarios) {
                            if (destinatario.isNotBlank()) {

                                val usuario =
                                    usuarioRepository.retornaUsarioPorEmail(
                                        destinatario
                                    )

                                val idDestinatario =
                                    if (usuario != null) usuario.id_usuario else null

                                if (idDestinatario != null) {
                                    alteracaoRepository.atualizarImportantePorIdEmail(
                                        isImportant.value,
                                        email.id_email,
                                        idDestinatario
                                    )
                                }
                            }
                        }
                    } else {
                        isImportant.value = !isImportant.value
                        alteracaoRepository.atualizarImportantePorIdEmail(
                            isImportant.value,
                            email.id_email,
                            usuarioSelecionado.value.id_usuario
                        )
                    }
                },
                onClickArchive = {
                    if (isTodasContasScreen) {
                        isArchive.value = !isArchive.value
                        for (destinatario in todosDestinatarios) {
                            if (destinatario.isNotBlank()) {
                                val usuario =
                                    usuarioRepository.retornaUsarioPorEmail(
                                        destinatario
                                    )

                                val idDestinatario =
                                    if (usuario != null) usuario.id_usuario else null

                                if (idDestinatario != null) {
                                    alteracaoRepository.atualizarArquivadoPorIdEmailEIdusuario(
                                        isArchive.value,
                                        email.id_email,
                                        idDestinatario
                                    )
                                }
                            }
                        }
                    } else {
                        isArchive.value = !isArchive.value
                        alteracaoRepository.atualizarArquivadoPorIdEmailEIdusuario(
                            isArchive.value,
                            email.id_email,
                            usuarioSelecionado.value.id_usuario
                        )
                    }
                },
                isSpam = isSpam,
                isImportant = isImportant,
                isArchive = isArchive
            )


            ColumnEmailDetails(
                onClickDraftRespostaEmailDelete = { respostaEmail ->
                    anexoRespostaEmailRepository.excluirAnexoPorIdRespostaEmail(
                        respostaEmail.id_resposta_email
                    )
                    respostaEmailRepository.excluirRespostaEmail(respostaEmail)
                    respostasEmailStateList.remove(respostaEmail)
                    Toast.makeText(
                        context,
                        toastMessageDraftMailDeleted,
                        Toast.LENGTH_LONG
                    ).show()

                },
                onClickDraftRespostaEmailEdit = { respostaEmail ->
                    navController.navigate("editarespostaemailscreen/${respostaEmail.id_resposta_email}")
                },
                email = email,
                anexoBitMapList = anexoBitMapList,
                timeState = timeState,
                usuarioSelecionado = usuarioSelecionado,
                anexoRespostaEmailRepository = anexoRespostaEmailRepository,
                respostasEmailStateList = respostasEmailStateList
            )
        }
    }
}
