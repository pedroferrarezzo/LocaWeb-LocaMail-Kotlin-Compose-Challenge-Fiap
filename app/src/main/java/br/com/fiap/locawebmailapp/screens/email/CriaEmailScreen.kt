package br.com.fiap.locawebmailapp.screens.email

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.validateEmail
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery


@Composable
fun CriaEmailScreen(navController: NavController) {

    val context = LocalContext.current

    val email = Email()
    val anexo = Anexo()
    val convidado = Convidado()

    val emailRepository = EmailRepository(context)
    val anexoRepository = AnexoRepository(context)
    val convidadoRepository = ConvidadoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)

    val assuntoText = remember {
        mutableStateOf("")
    }

    val corpoMailText = remember {
        mutableStateOf("")
    }

    val destinatarios = remember { mutableStateListOf<String>() }
    val destinatarioText = remember {
        mutableStateOf("")
    }
    val cc = remember {
        mutableStateOf("")
    }
    val ccs = remember { mutableStateListOf<String>() }
    val cco = remember {
        mutableStateOf("")
    }
    val ccos = remember { mutableStateListOf<String>() }


    val isErrorPara = remember {
        mutableStateOf(false)
    }

    val isErrorCc = remember {
        mutableStateOf(false)
    }

    val isErrorCco = remember {
        mutableStateOf(false)
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val bitmapList = remember {
        mutableStateListOf<Bitmap>()
    }

    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
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

    val toastMessageMailDraftSaved = stringResource(id = R.string.toast_maildraft_saved)
    val toastMessageMailSent = stringResource(id = R.string.toast_mail_sent)
    val toastMessageMailDest = stringResource(id = R.string.toast_mail_dest)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RowCrudMail(
            onClickBack = {
                if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty() || bitmapList.isNotEmpty() ||
                    assuntoText.value != "" ||
                    corpoMailText.value != ""
                ) {
                    email.remetente = usuarioSelecionado.value.email
                    email.destinatario =
                        if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                    email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                    email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                    email.assunto = assuntoText.value
                    email.corpo = corpoMailText.value
                    email.enviado = false
                    email.editavel = true
                    email.id_usuario = usuarioSelecionado.value.id_usuario
                    val rowId = emailRepository.criarEmail(email = email)

                    if (bitmapList.isNotEmpty()) {
                        anexo.id_email = rowId

                        for (bitmap in bitmapList) {
                            val byteArray = bitmapToByteArray(bitmap = bitmap)
                            anexo.anexo = byteArray
                            anexoRepository.criarAnexo(anexo)
                        }
                    }
                    Toast.makeText(context, toastMessageMailDraftSaved, Toast.LENGTH_LONG)
                        .show()
                }
                navController.popBackStack()

            },
            onClickPaperClip = {
                launcher.launch("image/*")
            },
            onClickSend = {
                if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                    email.remetente = usuarioSelecionado.value.email
                    email.destinatario =
                        if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                    email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                    email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                    email.assunto = assuntoText.value
                    email.corpo = corpoMailText.value
                    email.enviado = true
                    email.editavel = false
                    email.id_usuario = usuarioSelecionado.value.id_usuario
                    val rowId = emailRepository.criarEmail(email = email)

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
                                    alt_id_email = rowId,
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
                        anexo.id_email = rowId

                        for (bitmap in bitmapList) {
                            val byteArray = bitmapToByteArray(bitmap = bitmap)
                            anexo.anexo = byteArray
                            anexoRepository.criarAnexo(anexo)
                        }
                    }
                    Toast.makeText(context, toastMessageMailSent, Toast.LENGTH_LONG).show()
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