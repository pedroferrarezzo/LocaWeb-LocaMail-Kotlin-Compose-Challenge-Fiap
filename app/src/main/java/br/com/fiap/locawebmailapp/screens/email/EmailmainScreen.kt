package br.com.fiap.locawebmailapp.screens.email

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import br.com.fiap.locawebmailapp.components.email.PastaPickerDalog
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.components.user.UserSelectorDalog
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EMailMainScreen(navController: NavController) {
    val selectedDrawer = remember {
        mutableStateOf("2")
    }

    val selectedDrawerPasta = remember {
        mutableStateOf("")
    }

    val textSearchBar = remember {
        mutableStateOf("")
    }

    val textPastaCreator = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val emailRepository = EmailRepository(context)
    val anexoRepository = AnexoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)
    val pastaRepository = PastaRepository(context)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val timeState = rememberTimePickerState()

    val openDialogUserPicker = remember {
        mutableStateOf(false)
    }

    val openDialogPastaPicker = remember {
        mutableStateOf(false)
    }

    val usuariosExistentes = usuarioRepository.listarUsuariosNaoSelecionados()

    if (usuariosExistentes.isEmpty()) {
        usuarioRepository.criarUsuario(
            usuario = Usuario(
                email = "pedrof@teste.com.br",
                nome = "Pedro Ferrarezzo",
                selected_user = true,

                )
        )

        usuarioRepository.criarUsuario(
            Usuario(
                email = "marianaf@teste.com.br",
                nome = "Mariana Ferreira",
                selected_user = false,

                )
        )

        usuarioRepository.criarUsuario(
            Usuario(
                email = "tadeuf@teste.com.br",
                nome = "Tadeu Ferreira",
                selected_user = false,

                )
        )
    }

    val expandedPasta = remember {
        mutableStateOf(true)
    }

    val openDialogPastaCreator = remember {
        mutableStateOf(false)
    }


    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val receivedEmailList =
        emailRepository.listarEmailsPorDestinatario(
            usuarioSelecionado.value.email,
            usuarioSelecionado.value.id_usuario
        )

    val receivedStateEmailList = remember {
        mutableStateListOf<EmailComAlteracao>().apply {
            addAll(receivedEmailList)
        }
    }

    val listPastaState = remember {
        mutableStateListOf<Pasta>().apply {
            addAll(pastaRepository.listarPastasPorIdUsuario(usuarioSelecionado.value.id_usuario))
        }
    }


    val attachEmailList = anexoRepository.listarAnexosIdEmail()

    val redLcWeb = colorResource(id = R.color.lcweb_red_1)




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
        receivedEmailStateListRecompose = {
            receivedStateEmailList.addAll(
                emailRepository.listarEmailsPorDestinatario(
                    usuarioSelecionado.value.email,
                    usuarioSelecionado.value.id_usuario
                )
            )
        },
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
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
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
                                    contentDescription = ""
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

                UserSelectorDalog<EmailComAlteracao>(
                    openDialogUserPicker = openDialogUserPicker,
                    usuarioSelecionado,
                    usuariosExistentes,
                    receivedStateEmailList,
                    applyStateList = {
                        receivedStateEmailList.addAll(
                            emailRepository.listarEmailsPorDestinatario(
                                usuarioSelecionado.value.email,
                                usuarioSelecionado.value.id_usuario
                            )
                        )
                    },
                    usuarioRepository = usuarioRepository
                )

                if (receivedStateEmailList.isNotEmpty()) {

                    LazyColumn(reverseLayout = true) {
                        items(receivedStateEmailList, key = {
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
                                            Text(text = "Para: ${it.email.destinatario}")
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
                                                        contentDescription = "",
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

                                            Row(verticalAlignment = Alignment.CenterVertically) {

                                                if (listPastaState.isNotEmpty()) {
                                                    IconButton(onClick = {
                                                        openDialogPastaPicker.value =
                                                            !openDialogPastaPicker.value
                                                    }) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.box_archive_solid),
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .width(20.dp)
                                                                .height(20.dp)
                                                        )
                                                    }

                                                    PastaPickerDalog(
                                                        openDialogPastaPicker = openDialogPastaPicker,
                                                        listPasta = listPastaState,
                                                        color = redLcWeb,
                                                        onClickPasta = { pasta ->

                                                            Toast.makeText(context, "Email movido para a pasta ${pasta.nome}", Toast.LENGTH_LONG)
                                                                .show()

                                                            alteracaoRepository.atualizarPastaPorIdEmailEIdUsuario(
                                                                pasta = pasta.id_pasta,
                                                                id_email = it.alteracao.alt_id_email,
                                                                id_usuario = usuarioSelecionado.value.id_usuario

                                                            )

                                                            receivedStateEmailList.remove(it)

                                                            openDialogPastaPicker.value = false
                                                        }
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
                                                        contentDescription = "",
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
                        contentDescription = "",
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
        }

    }
}

