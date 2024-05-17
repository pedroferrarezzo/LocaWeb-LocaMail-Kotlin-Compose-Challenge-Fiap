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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import br.com.fiap.locawebmailapp.utils.stringParaLista

@Composable
fun VisualizaEmailScreen(
    navController: NavController,
    idEmail: Long,
    isTodasContasScreen: Boolean
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


    val respostasEmail = respostaEmailRepository.listarRespostasEmailPortIdEmail(idEmail)


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
                        contentDescription = "",
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.locaweb),
                    contentDescription = "",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)

                )


                Row {

                    IconButton(onClick = {
                        if (isExcluido.value) {
                            navController.popBackStack()

                            anexoRepository.excluirAnexoPorIdEmail(email.id_email)

                            alteracaoRepository.excluiAlteracaoPorIdEmailEIdUsuario(
                                email.id_email,
                                usuarioSelecionado.value.id_usuario
                            )

                            val alteracao =
                                alteracaoRepository.listarAlteracaoPorIdEmail(email.id_email)

                            if (alteracao == null) {
                                emailRepository.excluirEmail(email = email)
                            }

                            Toast.makeText(context, "Email excluído", Toast.LENGTH_LONG).show()

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
                                    "Email movido para as lixeiras",
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
                                    "Email movido para a lixeira",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            navController.popBackStack()
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "",
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
                                    contentDescription = "",
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
                                contentDescription = "",
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
                                contentDescription = "",
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
                                contentDescription = "",
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
                                    contentDescription = null,
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
                            text = "De:",
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
                            text = "Para:",
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
                                text = "Cc:",
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


                }



                items(respostasEmail) { respostaEmail ->

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

                        if (respostaEmail.editavel && usuarioSelecionado.value.id_usuario == respostaEmail.id_usuario) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                            )
                            Button(
                                onClick = {

                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = colorResource(id = R.color.lcweb_gray_1)
                                ),
                                shape = RectangleShape
                            ) {
                                Text(text = "Editar")
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
                                            contentDescription = null,
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
                                    text = "De:",
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
                                    text = "Para:",
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
                                        text = "Cc:",
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
                                            contentDescription = null,
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
                                    text = "De:",
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
                                    text = "Para:",
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
                                        text = "Cc:",
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

                            if (respostaEmail.corpo.isNotBlank()) {
                                Text(
                                    text = respostaEmail.corpo,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp),
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

