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
import androidx.compose.runtime.toMutableStateList
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
import br.com.fiap.locawebmailapp.database.repository.AgendaConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.AgendaRepository
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.RespostaEmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailsExcluidosScreen(navController: NavController) {
    val selectedDrawer = remember {
        mutableStateOf("8")
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
    val anexoRespostaEmailRepository = AnexoRespostaEmailRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)
    val pastaRepository = PastaRepository(context)
    val respostaEmailRepository = RespostaEmailRepository(context)
    val agendaRepository = AgendaRepository(context)
    val agendaConvidadoRepository = AgendaConvidadoRepository(context)

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

    val lixeiraEmailList =
        emailRepository.listarEmailsLixeiraPorIdUsuario(usuarioSelecionado.value.id_usuario)

    val lixeiraStateEmailLst = remember {
        mutableStateListOf<EmailComAlteracao>().apply {
            addAll(lixeiraEmailList)
        }
    }

    val openDialogPastaCreator = remember {
        mutableStateOf(false)
    }

    val textPastaCreator = remember {
        mutableStateOf("")
    }

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

    val toastMessageMailsDeleted = stringResource(id = R.string.toast_mails_delete)
    val toastMessageMailMovedFromTrash = stringResource(id = R.string.toast_mail_moved_from_trash)
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
                        val emails = emailRepository.listarEmailsLixeiraPorIdUsuario(
                            usuarioSelecionado.value.id_usuario
                        )
                        emails.forEach { email ->
                            if (!lixeiraStateEmailLst.contains(email)) {
                                lixeiraStateEmailLst.add(email)
                            }
                        }
                    },
                    usuarioSelecionado = usuarioSelecionado,
                    stateEmailList = lixeiraStateEmailLst,
                    usuarioRepository = usuarioRepository,
                    placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                    selectedDrawerPasta = selectedDrawerPasta,
                    navController = navController
                )

                if (lixeiraStateEmailLst.isNotEmpty()) {
                    TopButton(
                        textButton = stringResource(id = R.string.mail_generic_clean_all),
                        onClick = {
                            for (email in lixeiraEmailList) {

                                alteracaoRepository.excluiAlteracaoPorIdEmailEIdUsuario(
                                    email.email.id_email,
                                    usuarioSelecionado.value.id_usuario
                                )

                                val alteracao =
                                    alteracaoRepository.listarAlteracaoPorIdEmail(email.email.id_email)

                                if (alteracao.isEmpty()) {
                                    anexoRepository.excluirAnexoPorIdEmail(email.email.id_email)

                                    for (respostaEmail in respostaEmailRepository.listarRespostasEmailPorIdEmail(
                                        email.email.id_email
                                    )) {
                                        anexoRespostaEmailRepository.excluirAnexoPorIdRespostaEmail(
                                            respostaEmail.id_resposta_email
                                        )
                                    }

                                    respostaEmailRepository.excluirRespostaEmailPorIdEmail(email.email.id_email)


                                    emailRepository.excluirEmail(email = email.email)
                                }

                                lixeiraStateEmailLst.remove(email)

                                Toast.makeText(context, toastMessageMailsDeleted, Toast.LENGTH_LONG)
                                    .show()

                                val agendaEmailList = agendaRepository.listarAgendaPorIdEmailEIdUsuario(email.email.id_email, usuarioSelecionado.value.id_usuario)

                                if (agendaEmailList.isNotEmpty()) {
                                    for (agenda in agendaEmailList) {
                                        agendaConvidadoRepository.excluirPorIdAgenda(agenda.id_agenda)
                                        agendaRepository.excluiAgenda(agenda)
                                    }
                                }
                            }
                        })

                    LazyColumn(reverseLayout = false) {
                        items(lixeiraStateEmailLst.reversed(), key = {
                            it.alteracao.id_alteracao
                        }) {

                            if (
                                it.email.assunto.contains(textSearchBar.value, ignoreCase = true) ||
                                it.email.corpo.contains(textSearchBar.value, ignoreCase = true)
                            ) {
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
                                    attachEmailList = attachEmailList,
                                    timeState = timeState,
                                    email = it,
                                    moveIconOption = true,
                                    onClickMoveButton = {
                                        alteracaoRepository.atualizarExcluidoPorIdEmailEIdusuario(
                                            false,
                                            it.email.id_email,
                                            it.alteracao.alt_id_usuario
                                        )

                                        Toast.makeText(
                                            context,
                                            toastMessageMailMovedFromTrash,
                                            Toast.LENGTH_LONG
                                        ).show()

                                        lixeiraStateEmailLst.remove(it)
                                    },
                                    importantIconOption = false,
                                    usuarioSelecionado = usuarioSelecionado,
                                )
                            }
                        }
                    }
                }
            }

            if (lixeiraStateEmailLst.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.recycling),
                    contentDescription = stringResource(id = R.string.content_desc_recyclinimage),
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

