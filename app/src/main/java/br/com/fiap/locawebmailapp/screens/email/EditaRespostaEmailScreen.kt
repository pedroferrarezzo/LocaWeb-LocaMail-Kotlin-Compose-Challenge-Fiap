package br.com.fiap.locawebmailapp.screens.email

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.AnexoRespostaEmail
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import br.com.fiap.locawebmailapp.utils.isValidEmail
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery
import br.com.fiap.locawebmailapp.utils.stringParaLista
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun EditaRespostaEmailScreen(navController: NavController, idRespostaEmail: Long) {

    val context = LocalContext.current

    val respostaEmailRepository = RespostaEmailRepository(context)
    val emailRepository = EmailRepository(context)
    val anexoRespostaEmailRepository = AnexoRespostaEmailRepository(context)
    val convidadoRepository = ConvidadoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)


    val respostaEmail =
        respostaEmailRepository.listarRespostaEmailPorIdRespostaEmail(idRespostaEmail)
    val emailResponder = emailRepository.listarEmailPorId(respostaEmail.id_email)

    val anexoRespostaEmail = AnexoRespostaEmail()
    val convidado = Convidado()
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val alteracoesEmailAltIdUsuarioList =
        alteracaoRepository.listarAltIdUsuarioPorIdEmail(respostaEmail.id_email)

    val anexoRespostaEmailArrayByteList =
        anexoRespostaEmailRepository.listarAnexosArrayBytePorIdRespostaEmail(respostaEmail.id_resposta_email)
    val bitmapList = remember {
        anexoRespostaEmailArrayByteList.map {
            byteArrayToBitmap(it)
        }.toMutableStateList()
    }

    val destinatarios =
        remember {
            if (respostaEmail.destinatario.equals("")) mutableStateListOf<String>() else stringParaLista(
                respostaEmail.destinatario
            ).toMutableStateList()
        }

    val destinatarioText = remember {
        mutableStateOf("")
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val assuntoText = remember {
        mutableStateOf(respostaEmail.assunto)
    }

    val coporMailText = remember {
        mutableStateOf(respostaEmail.corpo)
    }

    val cc = remember {
        mutableStateOf("")
    }
    val ccs =
        remember {
            if (respostaEmail.cc.equals("")) mutableStateListOf<String>() else stringParaLista(
                respostaEmail.cc
            ).toMutableStateList()
        }

    val cco = remember {
        mutableStateOf("")
    }
    val ccos = remember {
        if (respostaEmail.cco.equals("")) mutableStateListOf<String>() else stringParaLista(
            respostaEmail.cco
        ).toMutableStateList()
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        if (listaParaString(destinatarios) != respostaEmail.destinatario
                            || listaParaString(ccos) != respostaEmail.cco
                            || listaParaString(ccs) != respostaEmail.cc
                            || assuntoText.value != respostaEmail.assunto
                            || coporMailText.value != respostaEmail.corpo
                            || bitmapList.isNotEmpty()
                            || anexoRespostaEmailArrayByteList.isNotEmpty()
                        ) {
                            respostaEmail.destinatario =
                                if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                            respostaEmail.cc =
                                if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                            respostaEmail.cco =
                                if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                            respostaEmail.assunto = assuntoText.value
                            respostaEmail.corpo = coporMailText.value
                            respostaEmail.enviado = false
                            respostaEmail.editavel = true
                            anexoRespostaEmail.id_resposta_email = respostaEmail.id_resposta_email

                            respostaEmailRepository.atualizarRespostaEmail(respostaEmail)
                            anexoRespostaEmailRepository.excluirAnexoPorIdRespostaEmail(
                                anexoRespostaEmail.id_resposta_email
                            )

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
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(id = R.string.content_desc_key_left),
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.locaweb),
                    contentDescription = stringResource(id = R.string.content_desc_lcweb_logo),
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)

                )

                Row {
                    IconButton(onClick = {
                        launcher.launch("image/*")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.paperclip_solid),
                            contentDescription = stringResource(id = R.string.content_desc_clips),
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }

                    IconButton(onClick = {
                        if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                            respostaEmail.destinatario =
                                if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                            respostaEmail.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                            respostaEmail.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                            respostaEmail.assunto = assuntoText.value
                            respostaEmail.corpo = coporMailText.value
                            respostaEmail.enviado = true
                            respostaEmail.editavel = false
                            respostaEmail.data = "${LocalDate.now()}"
                            respostaEmail.horario =
                                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                            anexoRespostaEmail.id_resposta_email = respostaEmail.id_resposta_email


                            respostaEmailRepository.atualizarRespostaEmail(respostaEmail)
                            anexoRespostaEmailRepository.excluirAnexoPorIdRespostaEmail(
                                anexoRespostaEmail.id_resposta_email
                            )

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

                            for (destinatario in todosDestinatarios) {
                                val convidadoExistente =
                                    convidadoRepository.verificarConvidadoExiste(destinatario)

                                if (convidadoExistente != destinatario) {
                                    convidado.email = destinatario
                                    convidadoRepository.criarConvidado(convidado)
                                }

                            }

                            for (usuario in usuarioRepository.listarUsuarios()) {
                                if (todosDestinatarios.contains(usuario.email) && !alteracoesEmailAltIdUsuarioList.contains(
                                        usuario.id_usuario
                                    )
                                ) {
                                    alteracaoRepository.criarAlteracao(
                                        Alteracao(
                                            alt_id_email = respostaEmail.id_email,
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
                                        id_email = respostaEmail.id_email,
                                        id_usuario = usuario.id_usuario
                                    )
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
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = stringResource(id = R.string.content_desc_mail_send),
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }
                }

            }

            LazyRow {
                items(bitmapList) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = stringResource(id = R.string.content_desc_img_selected),
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                        )
                        IconButton(
                            onClick = {
                                bitmapList.remove(it)
                            },
                            modifier = Modifier.padding(top = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(id = R.string.content_desc_clear),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    }
                }
            }

            Row {
                Text(
                    text = stringResource(id = R.string.mail_generic_from),
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.lcweb_red_1),
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = usuarioSelecionado.value.email,
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.lcweb_gray_1),
                    modifier = Modifier.padding(5.dp)
                )
            }
            HorizontalDivider(
                color = colorResource(id = R.color.lcweb_red_1)
            )

            TextField(
                value = destinatarioText.value,
                onValueChange = {
                    destinatarioText.value = it
                    if (isErrorPara.value) isErrorPara.value = false
                },
                leadingIcon = {
                    Text(
                        text = stringResource(id = R.string.mail_generic_to),
                        modifier = Modifier.padding(10.dp),
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                },
                trailingIcon = {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            expandedCc.value = !expandedCc.value
                        }) {
                            Icon(
                                imageVector = if (!expandedCc.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                contentDescription = stringResource(id = R.string.content_desc_expand_area),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }

                        IconButton(onClick = {
                            if (destinatarios.contains(destinatarioText.value) || ccs.contains(
                                    destinatarioText.value
                                ) || ccos.contains(destinatarioText.value)
                            ) {
                                isErrorPara.value = true
                            } else {
                                isErrorPara.value = !isValidEmail(destinatarioText.value)
                            }

                            if (!isErrorPara.value) destinatarios.add(destinatarioText.value.toLowerCase())
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = stringResource(id = R.string.content_desc_add),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1),
                    errorContainerColor = Color.Transparent,
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                ),
                singleLine = true,
                textStyle = TextStyle(
                    textDecoration = TextDecoration.None
                ),
                isError = isErrorPara.value
            )

            LazyRow() {
                items(destinatarios) { destinatario ->

                    Button(
                        onClick = {
                            if (!stringParaLista(emailResponder.destinatario).contains(destinatario) && !emailResponder.remetente.equals(
                                    destinatario
                                )
                            ) {
                                destinatarios.remove(destinatario)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.lcweb_red_1),
                            contentColor = colorResource(id = R.color.white)
                        ),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.padding(horizontal = 5.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = destinatario, fontSize = 10.sp)
                            if (!stringParaLista(emailResponder.destinatario).contains(destinatario) && !emailResponder.remetente.equals(
                                    destinatario
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = stringResource(id = R.string.content_desc_clear),
                                    modifier = Modifier
                                        .padding(start = 2.dp)
                                        .width(15.dp)
                                        .height(15.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (expandedCc.value) {
                TextField(
                    value = cc.value,
                    onValueChange = {
                        cc.value = it
                        if (isErrorCc.value) isErrorCc.value = false
                    },
                    leadingIcon = {
                        Text(
                            text = stringResource(id = R.string.mail_generic_cc),
                            modifier = Modifier.padding(10.dp),
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {

                            if (destinatarios.contains(cc.value) || ccs.contains(cc.value) || ccos.contains(
                                    cc.value
                                )
                            ) {
                                isErrorCc.value = true
                            } else {
                                isErrorCc.value = !isValidEmail(cc.value)
                            }
                            if (!isErrorCc.value) ccs.add(cc.value.toLowerCase())

                        }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = stringResource(id = R.string.content_desc_add),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.lcweb_red_1),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                        focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                        errorTextColor = colorResource(id = R.color.lcweb_red_1),
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                        errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                        errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                        errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                    ),
                    singleLine = true,
                    textStyle = TextStyle(
                        textDecoration = TextDecoration.None
                    ),
                    isError = isErrorCc.value
                )

                LazyRow() {
                    items(ccs) { cc ->

                        Button(
                            onClick = { ccs.remove(cc) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.lcweb_red_1),
                                contentColor = colorResource(id = R.color.white)
                            ),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.padding(horizontal = 5.dp),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = cc, fontSize = 10.sp)
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = stringResource(id = R.string.content_desc_clear),
                                    modifier = Modifier
                                        .padding(start = 2.dp)
                                        .width(15.dp)
                                        .height(15.dp)
                                )
                            }
                        }
                    }
                }



                TextField(
                    value = cco.value,
                    onValueChange = {
                        cco.value = it
                        if (isErrorCco.value) isErrorCco.value = false
                    },
                    leadingIcon = {
                        Text(
                            text = stringResource(id = R.string.mail_generic_cco),
                            modifier = Modifier.padding(10.dp),
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (destinatarios.contains(cco.value) || ccs.contains(cco.value) || ccos.contains(
                                    cco.value
                                )
                            ) {
                                isErrorCco.value = true
                            } else {
                                isErrorCco.value = !isValidEmail(cco.value)
                            }
                            if (!isErrorCco.value) ccos.add(cco.value.toLowerCase())
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = stringResource(id = R.string.content_desc_add),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.lcweb_red_1),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                        focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                        errorTextColor = colorResource(id = R.color.lcweb_red_1),
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                        errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                        errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                        errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                    ),
                    singleLine = true,
                    textStyle = TextStyle(
                        textDecoration = TextDecoration.None
                    ),
                    isError = isErrorCco.value
                )

                LazyRow() {
                    items(ccos) { cco ->

                        Button(
                            onClick = { ccos.remove(cco) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.lcweb_red_1),
                                contentColor = colorResource(id = R.color.white)
                            ),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.padding(horizontal = 5.dp),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = cco, fontSize = 10.sp)
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = stringResource(id = R.string.content_desc_clear),
                                    modifier = Modifier
                                        .padding(start = 2.dp)
                                        .width(15.dp)
                                        .height(15.dp)
                                )
                            }

                        }
                    }
                }


            }

            TextField(
                value = assuntoText.value,
                onValueChange = { assuntoText.value = it },
                leadingIcon = {
                    Text(
                        text = stringResource(id = R.string.mail_generic_subject),
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.padding(10.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1),
                    errorContainerColor = Color.Transparent,
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                ),
                singleLine = true,
                textStyle = TextStyle(
                    textDecoration = TextDecoration.None
                )

            )

            TextField(
                value = coporMailText.value,
                onValueChange = { coporMailText.value = it },
                placeholder = { Text(text = stringResource(id = R.string.mail_generic_body)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1),
                    errorContainerColor = Color.Transparent,
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                ),
                textStyle = TextStyle(
                    textDecoration = TextDecoration.None
                )
            )


        }
    }
}

