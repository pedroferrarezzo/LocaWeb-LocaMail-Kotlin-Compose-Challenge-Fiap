package br.com.fiap.locawebmailapp.screens.email

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.ColumnCrudMail
import br.com.fiap.locawebmailapp.components.email.RowCrudMail
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.AnexoRespostaEmail
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.validateEmail
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery
import br.com.fiap.locawebmailapp.utils.respostaEmailParaEmail
import br.com.fiap.locawebmailapp.utils.stringParaLista
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriaRespostaEmailScreen(navController: NavController, idEmail: Long, idRespostaEmail: Long?) {

    val context = LocalContext.current

    val respostaEmailRepository = RespostaEmailRepository(context)
    val emailRepository = EmailRepository(context)
    val anexoRespostaEmailRepository = AnexoRespostaEmailRepository(context)
    val convidadoRepository = ConvidadoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)


    val respostaEmail = RespostaEmail()
    var emailResponder = emailRepository.listarEmailPorId(idEmail)

    if (idRespostaEmail != null) {
        emailResponder = respostaEmailParaEmail(respostaEmailRepository.listarRespostaEmailPorIdRespostaEmail(idRespostaEmail))
    }

    val anexoRespostaEmail = AnexoRespostaEmail()
    val convidado = Convidado()
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val alteracoesEmailAltIdUsuarioList = alteracaoRepository.listarAltIdUsuarioPorIdEmail(idEmail)

    val bitmapList = remember {
        mutableStateListOf<Bitmap>()
    }

    val destinatarios =
        remember {
            if (emailResponder.id_usuario == usuarioSelecionado.value.id_usuario) stringParaLista(
                emailResponder.destinatario
            ).toMutableStateList() else stringParaLista(emailResponder.remetente).toMutableStateList()
        }

    val destinatarioText = remember {
        mutableStateOf("")
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val assuntoText = remember {
        mutableStateOf("")
    }

    val corpoMailText = remember {
        mutableStateOf("")
    }

    val cc = remember {
        mutableStateOf("")
    }
    val ccs =
        remember {
            mutableStateListOf<String>()
        }

    val cco = remember {
        mutableStateOf("")
    }
    val ccos = remember {
        mutableStateListOf<String>()
    }


    val isErrorPara = remember {
        mutableStateOf(false)
    }

    val isErrorCc = remember {
        mutableStateOf(false)
    }

    val isErrorCco = remember {
        mutableStateOf(false)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

            imageUri = uri
            pickImageFromGallery(
                context = context,
                imageUri = imageUri,
                bitmap = bitmap,
                bitmapList = bitmapList
            )
        }

    val expandedCc = remember {
        mutableStateOf(false)
    }

    val toastMessageDraftMailRespSaved = stringResource(id = R.string.toast_maildraftresp_saved)
    val toastMessageDraftMailRespSent = stringResource(id = R.string.toast_maildraft_sent)
    val toastMessageMailDest = stringResource(id = R.string.toast_mail_dest)

    if (respostaEmail != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            RowCrudMail(
                onClickBack = {
                    if (ccs.isNotEmpty() ||
                        ccos.isNotEmpty() || bitmapList.isNotEmpty() ||
                        assuntoText.value != "" ||
                        corpoMailText.value != ""
                    ) {
                        respostaEmail.remetente = usuarioSelecionado.value.email
                        respostaEmail.destinatario =
                            if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                        respostaEmail.cc =
                            if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                        respostaEmail.cco =
                            if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                        respostaEmail.assunto = assuntoText.value
                        respostaEmail.corpo = corpoMailText.value
                        respostaEmail.enviado = false
                        respostaEmail.editavel = true
                        respostaEmail.id_email = idEmail
                        respostaEmail.id_usuario = usuarioSelecionado.value.id_usuario

                        val rowId =
                            respostaEmailRepository.criarRespostaEmail(respostaEmail)

                        anexoRespostaEmail.id_resposta_email = rowId

                        if (bitmapList.isNotEmpty()) {
                            for (bitmap in bitmapList) {
                                val byteArray = bitmapToByteArray(bitmap = bitmap)
                                anexoRespostaEmail.anexo = byteArray
                                anexoRespostaEmailRepository.criarAnexo(anexoRespostaEmail)
                            }
                        }

                        Toast.makeText(
                            context,
                            toastMessageDraftMailRespSaved,
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    } else {
                        navController.popBackStack()
                    }
                },
                onClickPaperClip = { launcher.launch("image/*") },
                onClickSend = {
                    if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                        respostaEmail.remetente = usuarioSelecionado.value.email
                        respostaEmail.destinatario =
                            if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                        respostaEmail.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                        respostaEmail.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                        respostaEmail.assunto = assuntoText.value
                        respostaEmail.corpo = corpoMailText.value
                        respostaEmail.enviado = true
                        respostaEmail.editavel = false
                        respostaEmail.data = "${LocalDate.now()}"
                        respostaEmail.horario =
                            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                        respostaEmail.id_email = idEmail
                        respostaEmail.id_usuario = usuarioSelecionado.value.id_usuario
                        val rowId = respostaEmailRepository.criarRespostaEmail(respostaEmail)

                        anexoRespostaEmail.id_resposta_email = rowId

                        if (bitmapList.isNotEmpty()) {
                            for (bitmap in bitmapList) {
                                val byteArray = bitmapToByteArray(bitmap = bitmap)
                                anexoRespostaEmail.anexo = byteArray
                                anexoRespostaEmailRepository.criarAnexo(anexoRespostaEmail)
                            }

                        }

                        val todosDestinatarios = arrayListOf<String>()
                        todosDestinatarios.addAll(destinatarios)
                        todosDestinatarios.addAll(ccos)
                        todosDestinatarios.addAll(ccs)

                        if (!todosDestinatarios.contains(respostaEmail.remetente)) todosDestinatarios.add(
                            respostaEmail.remetente
                        )

                        for (usuario in usuarioRepository.listarUsuarios()) {

                            if (todosDestinatarios.contains(usuario.email) && !alteracoesEmailAltIdUsuarioList.contains(
                                    usuario.id_usuario
                                )
                            ) {
                                alteracaoRepository.criarAlteracao(
                                    Alteracao(
                                        alt_id_email = idEmail,
                                        alt_id_usuario = usuario.id_usuario
                                    )
                                )
                            }

                            if (todosDestinatarios.contains(usuario.email) && alteracoesEmailAltIdUsuarioList.contains(
                                    usuario.id_usuario
                                ) && usuario.id_usuario != usuarioSelecionado.value.id_usuario
                            ) {
                                alteracaoRepository.atualizarLidoPorIdEmailEIdusuario(
                                    false,
                                    id_email = idEmail,
                                    id_usuario = usuario.id_usuario
                                )
                            }
                        }

                        for (destinatario in todosDestinatarios) {
                            val convidadoExistente =
                                convidadoRepository.verificarConvidadoExiste(destinatario)

                            if (convidadoExistente != destinatario) {
                                convidado.email = destinatario
                                convidadoRepository.criarConvidado(convidado)
                            }

                        }

                        Toast.makeText(
                            context,
                            toastMessageDraftMailRespSent,
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(
                            context,
                            toastMessageMailDest,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                bitmapList = bitmapList
            )


            ColumnCrudMail(
                usuarioSelecionado = usuarioSelecionado,
                destinatarios = destinatarios,
                ccs = ccs,
                ccos = ccos,
                destinatarioText = destinatarioText,
                cc = cc,
                cco = cco,
                assuntoText = assuntoText,
                corpoMailText = corpoMailText,
                expandedCc = expandedCc,
                isErrorPara = isErrorPara,
                isErrorCc = isErrorCc,
                isErrorCco = isErrorCco,
                onValueChangeTextFieldTo = {
                    destinatarioText.value = it
                    if (isErrorPara.value) isErrorPara.value = false

                },
                onClickExpandCc = {
                    expandedCc.value = !expandedCc.value
                },
                onClickAddDestinatario = {
                    if (destinatarios.contains(destinatarioText.value) || ccs.contains(
                            destinatarioText.value
                        ) || ccos.contains(destinatarioText.value)
                    ) {
                        isErrorPara.value = true
                    } else {
                        isErrorPara.value = !validateEmail(destinatarioText.value)
                    }

                    if (!isErrorPara.value) destinatarios.add(destinatarioText.value.toLowerCase())
                },
                onClickRemoveDestinatario = {
                    if (!stringParaLista(emailResponder.destinatario).contains(it) && !emailResponder.remetente.equals(
                            it
                        )
                    ) {
                        destinatarios.remove(it)
                    }
                },
                onValueChangeTextFieldCc = {
                    cc.value = it
                    if (isErrorCc.value) isErrorCc.value = false
                },
                onClickAddCc = {
                    if (destinatarios.contains(cc.value) || ccs.contains(cc.value) || ccos.contains(
                            cc.value
                        )
                    ) {
                        isErrorCc.value = true
                    } else {
                        isErrorCc.value = !validateEmail(cc.value)
                    }
                    if (!isErrorCc.value) ccs.add(cc.value.toLowerCase())
                },
                onClickRemoveCc = {

                    ccs.remove(it)

                },
                onValueChangeTextFieldCco = {
                    cco.value = it
                    if (isErrorCco.value) isErrorCco.value = false

                },
                onClickAddCco = {
                    if (destinatarios.contains(cco.value) || ccs.contains(cco.value) || ccos.contains(
                            cco.value
                        )
                    ) {
                        isErrorCco.value = true
                    } else {
                        isErrorCco.value = !validateEmail(cco.value)
                    }
                    if (!isErrorCco.value) ccos.add(cco.value.toLowerCase())
                },
                onClickRemoveCco = {
                    ccos.remove(it)
                },
                onValueChangeTextFieldAssunto = {
                    assuntoText.value = it
                },
                onValueChangeTextFieldCorpoMail = {
                    corpoMailText.value = it
                },
                isRespostaEmail = true,
                showClearButtonAppear = {
                    (!stringParaLista(emailResponder.destinatario).contains(it) && !emailResponder.remetente.equals(
                        it
                    ))
                }
            )
        }
    }
}