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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.EmailCreateButton
import br.com.fiap.locawebmailapp.components.email.EmailDraftViewButton
import br.com.fiap.locawebmailapp.components.email.RowSearchBar
import br.com.fiap.locawebmailapp.components.email.TopButton
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Pasta


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailsEditaveisScreen(navController: NavController) {
    val selectedDrawer = remember {
        mutableStateOf("5")
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
    val pastaRepository = PastaRepository(context)


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

    val editableEmailList =
        emailRepository.listarEmailsEditaveisPorRemetente(usuarioSelecionado.value.email)
    val editableEmailStateList = remember {
        mutableStateListOf<Email>().apply {
            addAll(editableEmailList)
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

    val alteracaoRepository = AlteracaoRepository(context)
    val listPasta =
        pastaRepository.listarPastasPorIdUsuario(usuarioRepository.listarUsuarioSelecionado().id_usuario)

    val listPastaState = remember {
        mutableStateListOf<Pasta>().apply {
            addAll(listPasta)
        }
    }

    val toastMessageDraftsDeleted = stringResource(id = R.string.toast_maildrafts_deleted)
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
                        val emails = emailRepository.listarEmailsEditaveisPorRemetente(
                            usuarioSelecionado.value.email
                        )
                        emails.forEach { email ->
                            if (!editableEmailStateList.contains(email)) {
                                editableEmailStateList.add(email)
                            }
                        }
                    },
                    usuarioSelecionado = usuarioSelecionado,
                    stateEmailList = editableEmailStateList,
                    usuarioRepository = usuarioRepository,
                    placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                    selectedDrawerPasta = selectedDrawerPasta,
                    navController = navController
                )

                if (editableEmailStateList.isNotEmpty()) {

                    TopButton(
                        textButton = stringResource(id = R.string.mail_generic_clean_all),
                        onClick = {
                            Toast.makeText(context, toastMessageDraftsDeleted, Toast.LENGTH_LONG)
                                .show()

                            for (email in editableEmailList) {
                                anexoRepository.excluirAnexoPorIdEmail(email.id_email)
                                emailRepository.excluirEmail(email)
                                editableEmailStateList.remove(email)
                            }
                        })

                    LazyColumn(reverseLayout = false) {
                        items(editableEmailStateList.reversed(), key = {
                            it.id_email
                        }) {

                            if (
                                it.assunto.contains(textSearchBar.value, ignoreCase = true) ||
                                it.corpo.contains(textSearchBar.value, ignoreCase = true)
                            ) {

                                EmailDraftViewButton(
                                    onClickButton = { navController.navigate("editaemailscreen/${it.id_email}") },
                                    attachEmailList = attachEmailList,
                                    timeState = timeState,
                                    email = it
                                )
                            }
                        }
                    }
                }
            }

            if (editableEmailStateList.isEmpty()) {
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