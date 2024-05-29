package br.com.fiap.locawebmailapp.screens.ai

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.ai.QuestionDialog
import br.com.fiap.locawebmailapp.components.email.EmailViewButton
import br.com.fiap.locawebmailapp.components.email.RowSearchBar
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.database.repository.AiQuestionRepository
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
fun AiScreen(navController: NavController) {
    val selectedDrawer = remember {
        mutableStateOf("11")
    }

    val idEmail = remember {
        mutableStateOf(0L)
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
    val aiQuestionRepository = AiQuestionRepository(context)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val timeState = rememberTimePickerState()

    val openDialogUserPicker = remember {
        mutableStateOf(false)
    }

    val openQuestionDialog = remember {
        mutableStateOf(false)
    }

    val openDialogPastaPicker = remember {
        mutableStateOf(false)
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

    val question = remember {
        mutableStateOf("")
    }

    val receivedEmailList =
        emailRepository.listarEmailsAi(
            usuarioSelecionado.value.id_usuario
        )

    val listPastaState = remember {
        mutableStateListOf<Pasta>().apply {
            addAll(pastaRepository.listarPastasPorIdUsuario(usuarioSelecionado.value.id_usuario))
        }
    }


    val attachEmailList = anexoRepository.listarAnexosIdEmail()

    val redLcWeb = colorResource(id = R.color.lcweb_red_1)
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
                    LazyColumn() {
                        item {
                            Row(modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.Center) {
                                Image(
                                    painter = painterResource(id = R.drawable.aianalyse),
                                    contentDescription = stringResource(id = R.string.content_desc_aianalyse),
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(80.dp)
                                        .align(Alignment.CenterVertically))
                                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                    Text(
                                        text = stringResource(id = R.string.ai_summary),
                                        color = colorResource(id = R.color.lcweb_gray_1),
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        fontWeight = FontWeight.SemiBold)
                                    Text(
                                        text = stringResource(id = R.string.ai_main_topics),
                                        color = colorResource(id = R.color.lcweb_gray_1),
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }

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

                                        openQuestionDialog.value = true
                                        idEmail.value = it.email.id_email
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
                                    usuarioSelecionado = usuarioSelecionado,
                                )
                            }
                        }
                    }

                    QuestionDialog(
                        openQuestionDialog = openQuestionDialog ,
                        question = question,
                        aiQuestionRepository = aiQuestionRepository,
                        idEmail = idEmail.value,
                        navController = navController
                    )
                }
            }

            if (receivedEmailList.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.messagereceived),
                    contentDescription = stringResource(id = R.string.content_desc_nomailimage),
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.Center)
                )
            }
        }

    }
}