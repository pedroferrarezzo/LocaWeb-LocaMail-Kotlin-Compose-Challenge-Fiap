package br.com.fiap.locawebmailapp.screens.email

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.EmailCreateButton
import br.com.fiap.locawebmailapp.components.email.EmailViewButton
import br.com.fiap.locawebmailapp.components.email.RowSearchBar
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.Usuario


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
    val respostaEmailRepository = RespostaEmailRepository(context)

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
            usuarioSelecionado.value.id_usuario,
            respostaEmailRepository
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
    val toastMessageMailMovedFolder = stringResource(id = R.string.toast_mail_moved_folder)
    val toastMessageFolderDeleted = stringResource(id = R.string.toast_folder_deleted)

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
            val emails = emailRepository.listarEmailsPorDestinatario(
                usuarioSelecionado.value.email,
                usuarioSelecionado.value.id_usuario,
                respostaEmailRepository
            )
            emails.forEach { email ->
                if (!receivedStateEmailList.contains(email)) {
                    receivedStateEmailList.add(email)
                }
            }
        },
        context = context,
        listPastaState = listPastaState,
        scope = scope,
        toastMessageFolderDeleted = toastMessageFolderDeleted
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                RowSearchBar(
                    drawerState = drawerState,
                    scope = scope,
                    openDialogUserPicker = openDialogUserPicker,
                    textSearchBar = textSearchBar,
                    applyStateListUserSelectorDialog = {
                        val emails = emailRepository.listarEmailsPorDestinatario(
                            usuarioSelecionado.value.email,
                            usuarioSelecionado.value.id_usuario,
                            respostaEmailRepository
                        )
                        emails.forEach { email ->
                            if (!receivedStateEmailList.contains(email)) {
                                receivedStateEmailList.add(email)
                            }
                        }

                        listPastaState.clear()
                        listPastaState.addAll(pastaRepository.listarPastasPorIdUsuario(usuarioSelecionado.value.id_usuario))

                    },
                    usuarioSelecionado = usuarioSelecionado,
                    stateEmailList = receivedStateEmailList,
                    usuarioRepository = usuarioRepository,
                    placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                    selectedDrawerPasta = selectedDrawerPasta,
                    navController = navController
                )

                if (receivedStateEmailList.isNotEmpty()) {

                    LazyColumn() {
                        items(receivedStateEmailList.reversed(), key = {
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

                                val respostasEmail =
                                    respostaEmailRepository.listarRespostasEmailPorIdEmail(id_email = it.email.id_email)

                                EmailViewButton(
                                    onClickButton = {
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
                                    isRead = isRead,
                                    redLcWeb = redLcWeb,
                                    respostasEmail = respostasEmail,
                                    onClickPastaIconButton = {
                                        openDialogPastaPicker.value =
                                            !openDialogPastaPicker.value

                                    },
                                    onClickPastaPastaPickerDialog = { pasta ->

                                        Toast.makeText(
                                            context,
                                            "$toastMessageMailMovedFolder ${pasta.nome}",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()

                                        alteracaoRepository.atualizarPastaPorIdEmailEIdUsuario(
                                            pasta = pasta.id_pasta,
                                            id_email = it.alteracao.alt_id_email,
                                            id_usuario = usuarioSelecionado.value.id_usuario

                                        )

                                        receivedStateEmailList.remove(it)

                                        openDialogPastaPicker.value = false
                                    },
                                    onClickImportantButton = {
                                        isImportant.value = !isImportant.value
                                        alteracaoRepository.atualizarImportantePorIdEmail(
                                            isImportant.value,
                                            it.email.id_email,
                                            it.alteracao.alt_id_usuario
                                        )
                                    },
                                    isImportant = isImportant,
                                    attachEmailList = attachEmailList,
                                    listPastaState = listPastaState,
                                    timeState = timeState,
                                    openDialogPastaPicker = openDialogPastaPicker,
                                    email = it,
                                    usuarioSelecionado = usuarioSelecionado,
                                )
                            }
                        }
                    }
                }
            }

            if (receivedStateEmailList.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.messagereceived),
                    contentDescription = stringResource(id = R.string.content_desc_nomailimage),
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.Center)
                )
            }

            EmailCreateButton(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    navController.navigate("criaemailscreen")
                }
            )
        }

    }
}