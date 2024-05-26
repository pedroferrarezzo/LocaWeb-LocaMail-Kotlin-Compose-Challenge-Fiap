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
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Anexo
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import br.com.fiap.locawebmailapp.utils.validateEmail
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery
import br.com.fiap.locawebmailapp.utils.stringParaLista
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditaEmailScreen(navController: NavController, idEmail: Long) {

    val context = LocalContext.current


    val emailRepository = EmailRepository(context)
    val anexoRepository = AnexoRepository(context)
    val convidadoRepository = ConvidadoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)

    val email = emailRepository.listarEmailPorId(idEmail)
    val anexo = Anexo()
    val convidado = Convidado()
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val anexoArrayByteList = anexoRepository.listarAnexosArraybytePorIdEmail(idEmail)
    val bitmapList = remember {
        anexoArrayByteList.map {
            byteArrayToBitmap(it)
        }.toMutableStateList()
    }

    val destinatarios =
        remember {
            if (email.destinatario.equals("")) mutableStateListOf<String>() else stringParaLista(
                email.destinatario
            ).toMutableStateList()
        }
    val destinatarioText = remember {
        mutableStateOf("")
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val assuntoText = remember {
        mutableStateOf(email.assunto)
    }

    val corpoMailText = remember {
        mutableStateOf(email.corpo)
    }

    val cc = remember {
        mutableStateOf("")
    }
    val ccs =
        remember { if (email.cc.equals("")) mutableStateListOf<String>() else stringParaLista(email.cc).toMutableStateList() }


    val cco = remember {
        mutableStateOf("")
    }
    val ccos = remember {
        if (email.cco.equals("")) mutableStateListOf<String>() else stringParaLista(email.cco).toMutableStateList()
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

    val toastMessageDraftSaved = stringResource(id = R.string.toast_maildraft_saved)
    val toastMessageDraftDeleted = stringResource(id = R.string.toast_maildraft_deleted)
    val toastMessageMailSent = stringResource(id = R.string.toast_mail_sent)
    val toastMessageMailDest = stringResource(id = R.string.toast_mail_dest)

    if (email != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            RowCrudMail(
                onClickBack = {
                    if (listaParaString(destinatarios) != email.destinatario
                        || listaParaString(ccos) != email.cco
                        || listaParaString(ccs) != email.cc
                        || assuntoText.value != email.assunto
                        || corpoMailText.value != email.corpo
                        || bitmapList.isNotEmpty()
                        || anexoArrayByteList.isNotEmpty()
                    ) {
                        email.destinatario =
                            if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                        email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                        email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                        email.assunto = assuntoText.value
                        email.corpo = corpoMailText.value
                        email.enviado = false
                        email.editavel = true
                        anexo.id_email = email.id_email

                        emailRepository.atualizarEmail(email)
                        anexoRepository.excluirAnexoPorIdEmail(anexo.id_email)

                        if (bitmapList.isNotEmpty()) {
                            for (bitmap in bitmapList) {
                                val byteArray = bitmapToByteArray(bitmap = bitmap)
                                anexo.anexo = byteArray
                                anexoRepository.criarAnexo(anexo)
                            }
                        }
                        Toast.makeText(context, toastMessageDraftSaved, Toast.LENGTH_LONG).show()
                        navController.popBackStack()

                    } else {
                        navController.popBackStack()
                    }

                },
                onClickPaperClip = {
                    launcher.launch("image/*")
                },
                onClickSend = {
                    if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                        email.destinatario =
                            if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                        email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                        email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                        email.assunto = assuntoText.value
                        email.corpo = corpoMailText.value
                        email.enviado = true
                        email.editavel = false
                        email.data = "${LocalDate.now()}"
                        email.horario =
                            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                        anexo.id_email = email.id_email

                        emailRepository.atualizarEmail(email)
                        anexoRepository.excluirAnexoPorIdEmail(anexo.id_email)

                        val todosDestinatarios = arrayListOf<String>()
                        todosDestinatarios.addAll(destinatarios)
                        todosDestinatarios.addAll(ccos)
                        todosDestinatarios.addAll(ccs)

                        if (!todosDestinatarios.contains(email.remetente)) todosDestinatarios.add(
                            email.remetente
                        )

                        for (usuario in usuarioRepository.listarUsuarios()) {
                            if (todosDestinatarios.contains(usuario.email)) {
                                alteracaoRepository.criarAlteracao(
                                    Alteracao(
                                        alt_id_email = email.id_email,
                                        alt_id_usuario = usuario.id_usuario
                                    )
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

                        if (bitmapList.isNotEmpty()) {
                            for (bitmap in bitmapList) {
                                val byteArray = bitmapToByteArray(bitmap = bitmap)
                                anexo.anexo = byteArray
                                anexoRepository.criarAnexo(anexo)
                            }

                        }
                        Toast.makeText(context, toastMessageMailSent, Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, toastMessageMailDest, Toast.LENGTH_LONG).show()
                    }

                },
                bitmapList = bitmapList,
                isDraft = true,
                onClickDeleteDraft = {
                    navController.popBackStack()
                    anexoRepository.excluirAnexoPorIdEmail(email.id_email)
                    emailRepository.excluirEmail(email = email)
                    Toast.makeText(context, toastMessageDraftDeleted, Toast.LENGTH_LONG).show()
                })

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
                onClickExpandCc = { expandedCc.value = !expandedCc.value },
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
                    destinatarios.remove(it)
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
                }
            )
        }
    }
}