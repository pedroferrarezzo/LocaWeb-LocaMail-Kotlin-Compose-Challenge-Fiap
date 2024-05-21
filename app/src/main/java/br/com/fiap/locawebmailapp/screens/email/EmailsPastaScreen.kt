package br.com.fiap.locawebmailapp.screens.email

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.components.user.UserSelectorDalog
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailsPastaScreen(navController: NavController, id_pasta: Long) {
    val selectedDrawer = remember {
        mutableStateOf("")
    }

    val selectedDrawerPasta = remember {
        mutableStateOf(id_pasta.toString())
    }

    val textSearchBar = remember {
        mutableStateOf("")
    }

    val expandedPasta = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val emailRepository = EmailRepository(context)
    val anexoRepository = AnexoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)
    val pastaRepository = PastaRepository(context)
    val respostaEmailRepository = RespostaEmailRepository(context)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val timeState = rememberTimePickerState()

    val openDialogUserPicker = remember {
        mutableStateOf(false)
    }
    val usuariosExistentes = usuarioRepository.listarUsuariosNaoSelecionados()
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val pastaEmailList =
        emailRepository.listarEmailsPorPastaEIdUsuario(usuarioSelecionado.value.id_usuario, id_pasta)

    val pastaStateEmailLst = remember {
        mutableStateListOf<EmailComAlteracao>().apply {
            addAll(pastaEmailList)
        }
    }

    val openDialogPastaCreator = remember {
        mutableStateOf(false)
    }

    val textPastaCreator = remember {
        mutableStateOf("")
    }

    val attachEmailList = anexoRepository.listarAnexosIdEmail()

    val listPasta =
        pastaRepository.listarPastasPorIdUsuario(usuarioRepository.listarUsuarioSelecionado().id_usuario)

    val listPastaState = remember {
        mutableStateListOf<Pasta>().apply {
            addAll(listPasta)
        }
    }

    val toastMessageMailMovedFromFolder = stringResource(id = R.string.toast_mail_moved_from_folder)

    ModalNavDrawer(
        selectedDrawer = selectedDrawer,
        navController = navController,
        drawerState = drawerState,
        usuarioRepository = usuarioRepository,
        pastaRepository = pastaRepository,
        scrollState = rememberScrollState(),
        expandedPasta = expandedPasta,
        openDialogPastaCreator = openDialogPastaCreator,
        textPastaCreator = textPastaCreator,
        selectedDrawerPasta = selectedDrawerPasta,
        alteracaoRepository = alteracaoRepository,
        context = context,
        listPastaState = listPastaState
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = textSearchBar.value,
                        onValueChange = {
                            textSearchBar.value = it
                        },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.mail_main_searchbar)
                            )
                        },
                        leadingIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = stringResource(
                                    id = R.string.content_desc_menu
                                ))
                            }
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    openDialogUserPicker.value = !openDialogUserPicker.value
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = stringResource(id = R.string.content_desc_user)
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.lcweb_red_1),
                            unfocusedContainerColor = colorResource(id = R.color.lcweb_red_1),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLeadingIconColor = colorResource(id = R.color.white),
                            unfocusedLeadingIconColor = colorResource(id = R.color.white),
                            focusedPlaceholderColor = colorResource(id = R.color.white),
                            unfocusedPlaceholderColor = colorResource(id = R.color.white),
                            cursorColor = colorResource(id = R.color.white),
                            focusedTextColor = colorResource(id = R.color.white),
                            unfocusedTextColor = colorResource(id = R.color.white),
                            focusedSupportingTextColor = Color.Transparent,
                            unfocusedSupportingTextColor = Color.Transparent,
                            focusedTrailingIconColor = colorResource(id = R.color.white),
                            unfocusedTrailingIconColor = colorResource(id = R.color.white)
                        ),
                        textStyle = TextStyle(
                            textDecoration = TextDecoration.None
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(5.dp)
                    )
                }

                UserSelectorDalog(
                    openDialogUserPicker = openDialogUserPicker,
                    usuarioSelecionado,
                    usuariosExistentes,
                    pastaStateEmailLst,
                    applyStateList = {
                        pastaStateEmailLst.addAll(
                            emailRepository.listarEmailsPorPastaEIdUsuario(
                                usuarioSelecionado.value.id_usuario,
                                id_pasta
                            )
                        )

                    },
                    usuarioRepository = usuarioRepository
                )


                if (pastaStateEmailLst.isNotEmpty()) {
                    LazyColumn(reverseLayout = true) {
                        items(pastaStateEmailLst, key = {
                            it.alteracao.id_alteracao
                        }) {

                            if (
                                it.email.assunto.contains(textSearchBar.value, ignoreCase = true) ||
                                it.email.corpo.contains(textSearchBar.value, ignoreCase = true)
                            ) {
                                val isImportant = remember {
                                    mutableStateOf(it.alteracao.importante)
                                }

                                val isRead = remember {
                                    mutableStateOf(it.alteracao.lido)
                                }

                                val redLcWeb = colorResource(id = R.color.lcweb_red_1)

                                val respostasEmail =
                                    respostaEmailRepository.listarRespostasEmailPorIdEmail(id_email = it.email.id_email)



                                Button(
                                    onClick = {
                                        if (!isRead.value) {
                                            isRead.value = true
                                            alteracaoRepository.atualizarLidoPorIdEmailEIdusuario(
                                                isRead.value,
                                                it.email.id_email,
                                                it.alteracao.alt_id_usuario
                                            )
                                        }

                                        navController.navigate("visualizaemailscreen/${it.email.id_email}/false")
                                    },
                                    modifier =

                                    if (isRead.value) {
                                        Modifier
                                            .fillMaxWidth()
                                    } else {
                                        Modifier
                                            .fillMaxWidth()
                                            .drawBehind {
                                                val borderSize = 2.dp.toPx()
                                                val y = size.height - borderSize / 2

                                                drawLine(
                                                    redLcWeb,
                                                    Offset(0f, y),
                                                    Offset(size.width, y),
                                                    borderSize
                                                )
                                            }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = colorResource(id = R.color.lcweb_gray_1)
                                    ),
                                    shape = RectangleShape,
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 2.dp)
                                        ) {
                                            Row {
                                                if (respostasEmail.isNotEmpty()) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.reply_solid),
                                                        contentDescription = stringResource(id = R.string.content_desc_lcweb_reply),
                                                        modifier = Modifier
                                                            .width(20.dp)
                                                            .height(20.dp)
                                                            .padding(horizontal = 5.dp)
                                                    )
                                                }

                                                Text(
                                                    text = if (it.email.destinatario.length > 25) {
                                                        "${stringResource(id = R.string.mail_generic_to)} ${it.email.destinatario.take(25)}..."
                                                    } else {
                                                        "${stringResource(id = R.string.mail_generic_to)} ${it.email.destinatario}"
                                                    },
                                                    maxLines = 1
                                                )
                                            }
                                            Text(text = it.email.assunto)
                                            Text(
                                                text = if (it.email.corpo.length > 25) {
                                                    "${it.email.corpo.take(25)}..."
                                                } else {
                                                    it.email.corpo
                                                },
                                                maxLines = 1
                                            )
                                        }

                                        Column(
                                            modifier = Modifier.padding(horizontal = 2.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {


                                                if (attachEmailList.contains(it.email.id_email)) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.paperclip_solid),
                                                        contentDescription = stringResource(id = R.string.content_desc_clips),
                                                        modifier = Modifier
                                                            .width(20.dp)
                                                            .height(20.dp)
                                                            .padding(horizontal = 5.dp)
                                                    )
                                                }

                                                Text(
                                                    text = if (timeState.is24hour) it.email.horario else convertTo12Hours(
                                                        it.email.horario
                                                    )
                                                )
                                            }


                                            Row {
                                                IconButton(onClick = {
                                                    alteracaoRepository.atualizarPastaPorIdEmailEIdUsuario(
                                                        null,
                                                        it.email.id_email,
                                                        it.alteracao.alt_id_usuario
                                                    )

                                                    Toast.makeText(
                                                        context,
                                                        toastMessageMailMovedFromFolder,
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                    pastaStateEmailLst.remove(it)
                                                }) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.turn_up_solid),
                                                        contentDescription = stringResource(id = R.string.content_desc_up),
                                                        modifier = Modifier
                                                            .width(20.dp)
                                                            .height(20.dp)
                                                    )
                                                }

                                                IconButton(onClick = {
                                                    isImportant.value = !isImportant.value
                                                    alteracaoRepository.atualizarImportantePorIdEmail(
                                                        isImportant.value,
                                                        it.email.id_email,
                                                        it.alteracao.alt_id_usuario
                                                    )


                                                }) {
                                                    Icon(
                                                        imageVector = if (isImportant.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                                        contentDescription = stringResource(id = R.string.content_desc_favorite),
                                                        modifier = Modifier
                                                            .width(20.dp)
                                                            .height(20.dp)
                                                    )
                                                }
                                            }


                                        }
                                    }
                                }
                            }
                        }


                    }
                }
            }



            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd)
            ) {

                Button(
                    onClick = {
                        navController.navigate("criaemailscreen")
                    },
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lcweb_red_1)),
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = stringResource(id = R.string.content_desc_mail_box),
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}
