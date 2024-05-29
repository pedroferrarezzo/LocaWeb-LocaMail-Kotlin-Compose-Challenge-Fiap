package br.com.fiap.locawebmailapp.screens.email

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import br.com.fiap.locawebmailapp.components.email.TopButton
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta


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
        emailRepository.listarEmailsPorPastaEIdUsuario(
            usuarioSelecionado.value.id_usuario,
            id_pasta
        )

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
    val toastMessageMailsMovedFromFolder =
        stringResource(id = R.string.toast_mails_moved_from_folder)
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
                        val emails = emailRepository.listarEmailsPorPastaEIdUsuario(
                            usuarioSelecionado.value.id_usuario,
                            id_pasta
                        )
                        emails.forEach { email ->
                            if (!pastaStateEmailLst.contains(email)) {
                                pastaStateEmailLst.add(email)
                            }
                        }
                    },
                    usuarioSelecionado = usuarioSelecionado,
                    stateEmailList = pastaStateEmailLst,
                    usuarioRepository = usuarioRepository,
                    placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                    selectedDrawerPasta = selectedDrawerPasta,
                    navController = navController
                )


                if (pastaStateEmailLst.isNotEmpty()) {

                    TopButton(
                        textButton = stringResource(id = R.string.mail_generic_move_all),
                        onClick = {
                            for (pasta in pastaEmailList) {
                                alteracaoRepository.atualizarPastaPorIdEmailEIdUsuario(
                                    null,
                                    pasta.email.id_email,
                                    pasta.alteracao.alt_id_usuario
                                )

                                pastaStateEmailLst.remove(pasta)
                            }

                            Toast.makeText(
                                context,
                                toastMessageMailsMovedFromFolder,
                                Toast.LENGTH_LONG
                            )
                                .show()
                        })

                    LazyColumn(reverseLayout = false) {
                        items(pastaStateEmailLst.reversed(), key = {
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
                                    timeState = timeState,
                                    email = it,
                                    moveIconOption = true,
                                    onClickMoveButton = {
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

                                    },
                                    usuarioSelecionado = usuarioSelecionado,
                                )
                            }
                        }
                    }
                }
            }

            if (pastaStateEmailLst.isEmpty()) {
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

