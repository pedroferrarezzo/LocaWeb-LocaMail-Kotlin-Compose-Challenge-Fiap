package br.com.fiap.locawebmailapp.screens.email

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import br.com.fiap.locawebmailapp.utils.dateToCompleteStringDate
import br.com.fiap.locawebmailapp.utils.stringParaLista
import br.com.fiap.locawebmailapp.utils.stringToLocalDate

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
        todosDestinatarios.addAll(stringParaLista(email.destinatario))
        todosDestinatarios.addAll(stringParaLista(email.cc))
        todosDestinatarios.addAll(stringParaLista(email.cco))

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


    val toastMessageMailDeleted = stringResource(id = R.string.toast_mail_delete)
    val toastMessageDraftMailDeleted = stringResource(id = R.string.toast_maildraftresp_delete)
    val toastMessageMailMovedToTrashBin = stringResource(id = R.string.toast_mail_moved_trash_bin)
    val toastMessageMailMovedToTrash = stringResource(id = R.string.toast_mail_moved_trash)



    if (email != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }

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
                        if (isExcluido.value) {

                            alteracaoRepository.excluiAlteracaoPorIdEmailEIdUsuario(
                                email.id_email,
                                usuarioSelecionado.value.id_usuario
                            )

                            val alteracao =
                                alteracaoRepository.listarAlteracaoPorIdEmail(email.id_email)

                            if (alteracao.isEmpty()) {
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

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.content_desc_trash),
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }

                    if (!isExcluido.value) {

                        if (!isTodasContasScreen) {
                            IconButton(onClick = {
                                navController.navigate("criarespostaemailscreen/${idEmail}")

                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.reply_solid),
                                    contentDescription = stringResource(id = R.string.content_desc_lcweb_reply),
                                    modifier = Modifier
                                        .width(25.dp)
                                        .height(25.dp),
                                    tint = colorResource(id = R.color.lcweb_gray_1)
                                )
                            }
                        }

                        IconButton(onClick = {

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

                        }) {
                            Icon(
                                painter = if (isSpam.value) painterResource(id = R.drawable.exclamation_mark_filled) else painterResource(
                                    id = R.drawable.exclamation_mark_outlined
                                ),
                                contentDescription = stringResource(id = R.string.content_desc_spam),
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp),
                                tint = colorResource(id = R.color.lcweb_red_1)
                            )
                        }

                        IconButton(onClick = {

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
                        }) {
                            Icon(
                                painter = if (isArchive.value) painterResource(id = R.drawable.folder_open_solid) else painterResource(
                                    id = R.drawable.folder_open_regular
                                ),
                                contentDescription = stringResource(id = R.string.content_desc_folder),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }

                        IconButton(onClick = {
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

                        }) {
                            Icon(
                                imageVector = if (isImportant.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.content_desc_favorite),
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    }

                }
            }


            LazyColumn {
                item {
                    if (email.assunto.isNotBlank()) {
                        Text(
                            text = email.assunto,
                            fontSize = 30.sp,
                            lineHeight = 30.sp,
                            color = colorResource(id = R.color.lcweb_gray_1),
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    LazyRow(modifier = Modifier.padding(bottom = 5.dp)) {
                        items(anexoBitMapList) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                            ) {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = stringResource(id = R.string.content_desc_img_selected),
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(70.dp)
                                )
                            }
                        }
                    }


                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )

                    Row {
                        Text(
                            text = stringResource(id = R.string.mail_generic_from),
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_red_1),
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = email.remetente,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_gray_1),
                            modifier = Modifier.padding(5.dp)

                        )
                    }

                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )

                    Row {
                        Text(
                            text = stringResource(id = R.string.mail_generic_to),
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_red_1),
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = email.destinatario,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_gray_1),
                            modifier = Modifier.padding(5.dp)

                        )
                    }

                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )

                    if (email.cc.isNotBlank()) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.mail_generic_cc),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = email.cc,
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    }

                    Row {
                        Text(
                            text = stringResource(id = R.string.mail_generic_date),
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_red_1),
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = dateToCompleteStringDate(stringToLocalDate(email.data)) + " " + if (timeState.is24hour) email.horario else convertTo12Hours(
                                email.horario
                            ),
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_gray_1),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )

                    if (email.corpo.isNotBlank()) {
                        Text(
                            text = email.corpo,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_gray_1),
                            modifier = Modifier.padding(5.dp),
                            textAlign = TextAlign.Justify
                        )

                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                    )
                }

                items(respostasEmailStateList) { respostaEmail ->

                    val anexoRespostaEmailArrayByteList =
                        anexoRespostaEmailRepository.listarAnexosArrayBytePorIdRespostaEmail(
                            respostaEmail.id_resposta_email
                        )
                    val anexoRespostaEmailBitMapList = anexoRespostaEmailArrayByteList.map {
                        byteArrayToBitmap(it)
                    }


                    if (
                        stringParaLista(respostaEmail.destinatario).contains(usuarioSelecionado.value.email) ||
                        stringParaLista(respostaEmail.remetente).contains(usuarioSelecionado.value.email) ||
                        stringParaLista(respostaEmail.cc).contains(usuarioSelecionado.value.email) ||
                        stringParaLista(respostaEmail.cco).contains(usuarioSelecionado.value.email)
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        )

                        if (respostaEmail.editavel && usuarioSelecionado.value.id_usuario == respostaEmail.id_usuario) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Button(
                                    onClick = {
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
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = colorResource(id = R.color.lcweb_gray_1)
                                    ),
                                    shape = RectangleShape
                                ) {
                                    Text(text = stringResource(id = R.string.mail_generic_delete))
                                }


                                Button(
                                    onClick = {
                                        navController.navigate("editarespostaemailscreen/${respostaEmail.id_resposta_email}")

                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = colorResource(id = R.color.lcweb_gray_1)
                                    ),
                                    shape = RectangleShape
                                ) {
                                    Text(text = stringResource(id = R.string.mail_generic_edit))
                                }

                            }

                            if (respostaEmail.assunto.isNotBlank()) {
                                Text(
                                    text = respostaEmail.assunto,
                                    fontSize = 30.sp,
                                    lineHeight = 30.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }

                            LazyRow(modifier = Modifier.padding(bottom = 5.dp)) {
                                items(anexoRespostaEmailBitMapList) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(
                                            horizontal = 5.dp,
                                            vertical = 5.dp
                                        )
                                    ) {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = stringResource(id = R.string.content_desc_img_selected),
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(70.dp)
                                        )
                                    }
                                }
                            }


                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )

                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_from),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = respostaEmail.remetente,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)

                                )
                            }

                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )

                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_to),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = respostaEmail.destinatario,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)

                                )
                            }

                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )

                            if (respostaEmail.cc.isNotBlank()) {
                                Row {
                                    Text(
                                        text = stringResource(id = R.string.mail_generic_cc),
                                        fontSize = 15.sp,
                                        color = colorResource(id = R.color.lcweb_red_1),
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    Text(
                                        text = respostaEmail.cc,
                                        fontSize = 15.sp,
                                        color = colorResource(id = R.color.lcweb_gray_1),
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                                HorizontalDivider(
                                    color = colorResource(id = R.color.lcweb_red_1)
                                )
                            }

                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_date),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = dateToCompleteStringDate(stringToLocalDate(email.data)) + " " + if (timeState.is24hour) email.horario else convertTo12Hours(
                                        email.horario
                                    ),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )


                            if (respostaEmail.corpo.isNotBlank()) {
                                Text(
                                    text = respostaEmail.corpo,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp),
                                    textAlign = TextAlign.Justify
                                )
                            }

                        } else if (!respostaEmail.editavel) {
                            if (respostaEmail.assunto.isNotBlank()) {
                                Text(
                                    text = respostaEmail.assunto,
                                    fontSize = 30.sp,
                                    lineHeight = 30.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }

                            LazyRow(modifier = Modifier.padding(bottom = 5.dp)) {
                                items(anexoRespostaEmailBitMapList) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(
                                            horizontal = 5.dp,
                                            vertical = 5.dp
                                        )
                                    ) {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = stringResource(id = R.string.content_desc_img_selected),
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(70.dp)
                                        )
                                    }
                                }
                            }


                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )

                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_from),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = respostaEmail.remetente,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)

                                )
                            }

                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )

                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_to),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = respostaEmail.destinatario,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)

                                )
                            }

                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )

                            if (respostaEmail.cc.isNotBlank()) {
                                Row {
                                    Text(
                                        text = stringResource(id = R.string.mail_generic_cc),
                                        fontSize = 15.sp,
                                        color = colorResource(id = R.color.lcweb_red_1),
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    Text(
                                        text = respostaEmail.cc,
                                        fontSize = 15.sp,
                                        color = colorResource(id = R.color.lcweb_gray_1),
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                                HorizontalDivider(
                                    color = colorResource(id = R.color.lcweb_red_1)
                                )
                            }

                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_date),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = dateToCompleteStringDate(stringToLocalDate(email.data)) + " " + if (timeState.is24hour) email.horario else convertTo12Hours(
                                        email.horario
                                    ),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )

                            if (respostaEmail.corpo.isNotBlank()) {
                                Text(
                                    text = respostaEmail.corpo,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp),
                                    textAlign = TextAlign.Justify
                                )

                                HorizontalDivider(
                                    color = colorResource(id = R.color.lcweb_red_1)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

