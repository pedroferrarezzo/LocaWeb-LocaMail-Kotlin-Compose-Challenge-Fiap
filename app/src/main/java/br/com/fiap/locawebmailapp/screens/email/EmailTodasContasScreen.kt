package br.com.fiap.locawebmailapp.screens.email

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import br.com.fiap.locawebmailapp.utils.atualizarIsImportantParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.atualizarTodosDestinatariosList
import br.com.fiap.locawebmailapp.utils.atualizarisReadParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.stringParaLista


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTodasContasScreen(navController: NavController) {
    val selectedDrawer = remember {
        mutableStateOf("1")
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

    val openDialogPastaCreator = remember {
        mutableStateOf(false)
    }

    val textPastaCreator = remember {
        mutableStateOf("")
    }

    val receivedEmailList =
        emailRepository.listarTodosEmails(usuarioRepository)
    val attachEmailList = anexoRepository.listarAnexosIdEmail()

    val selectedDrawerPasta = remember {
        mutableStateOf("")
    }


    val listPasta =
        pastaRepository.listarPastasPorIdUsuario(usuarioRepository.listarUsuarioSelecionado().id_usuario)

    val listPastaState = remember {
        mutableStateListOf<Pasta>().apply {
            addAll(listPasta)
        }
    }

    val todosDestinatarios = arrayListOf<String>()
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
                RowSearchBar<EmailComAlteracao>(
                    drawerState = drawerState,
                    scope = scope,
                    openDialogUserPicker = openDialogUserPicker,
                    textSearchBar = textSearchBar,
                    usuarioSelecionado = usuarioSelecionado,
                    usuarioRepository = usuarioRepository,
                    placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                    selectedDrawerPasta = selectedDrawerPasta,
                    navController = navController
                )

                if (receivedEmailList.isNotEmpty()) {

                    LazyColumn(reverseLayout = false) {
                        items(receivedEmailList.reversed(), key = {
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

                                val respostasEmailList =
                                    respostaEmailRepository.listarRespostasEmailPorIdEmail(id_email = it.email.id_email)

                                atualizarTodosDestinatariosList(
                                    todosDestinatarios,
                                    it.email,
                                    respostasEmailList
                                )

                                atualizarIsImportantParaUsuariosRelacionados(
                                    todosDestinatarios,
                                    usuarioRepository,
                                    alteracaoRepository,
                                    isImportant,
                                    it.email
                                )

                                atualizarisReadParaUsuariosRelacionados(
                                    todosDestinatarios,
                                    usuarioRepository,
                                    alteracaoRepository,
                                    isRead,
                                    it.email
                                )

                                EmailViewButton(
                                    onClickButton = {
                                        if (!isRead.value) {
                                            isRead.value = true
                                            for (destinatario in (stringParaLista(it.email.destinatario) + stringParaLista(
                                                it.email.cc
                                            ) + stringParaLista(it.email.cco))) {
                                                if (destinatario.isNotBlank()) {
                                                    val usuario =
                                                        usuarioRepository.retornaUsarioPorEmail(
                                                            destinatario
                                                        )
                                                    val idDestinatario =
                                                        if (usuario != null) usuario.id_usuario else null

                                                    if (idDestinatario != null) {
                                                        alteracaoRepository.atualizarLidoPorIdEmailEIdusuario(
                                                            isRead.value,
                                                            it.email.id_email,
                                                            idDestinatario
                                                        )
                                                    }


                                                }
                                            }
                                        }

                                        navController.navigate("visualizaemailscreen/${it.email.id_email}/true")
                                    },
                                    isRead = isRead,
                                    redLcWeb = redLcWeb,
                                    respostasEmail = respostasEmailList,
                                    onClickImportantButton = {
                                        isImportant.value = !isImportant.value

                                        for (destinatario in (stringParaLista(it.email.destinatario) + stringParaLista(
                                            it.email.cc
                                        ) + stringParaLista(it.email.cco))) {
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
                                                        it.email.id_email,
                                                        idDestinatario
                                                    )
                                                }

                                            }
                                        }
                                    },
                                    isImportant = isImportant,
                                    attachEmailList = attachEmailList,
                                    timeState = timeState,
                                    email = it,
                                    usuarioSelecionado = usuarioSelecionado,
                                )
                            }
                        }
                    }
                }
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