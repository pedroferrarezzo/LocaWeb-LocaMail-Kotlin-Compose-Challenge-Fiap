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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import br.com.fiap.locawebmailapp.components.email.EmailCreateButton
import br.com.fiap.locawebmailapp.components.email.EmailViewButton
import br.com.fiap.locawebmailapp.components.email.RowSearchBar
import br.com.fiap.locawebmailapp.components.email.TopButton
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.components.user.UserSelectorDalog
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRespostaEmailRepository
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
                    usuariosExistentes = usuariosExistentes,
                    stateEmailList = lixeiraStateEmailLst,
                    usuarioRepository = usuarioRepository,
                    placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                    selectedDrawerPasta = selectedDrawerPasta,
                    navController = navController
                )

                if (lixeiraStateEmailLst.isNotEmpty()) {
                    TopButton(
                        textButton = stringResource(id = R.string.mail_generic_move_all),
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
                            }
                        })

                    LazyColumn(reverseLayout = true) {
                        items(lixeiraStateEmailLst, key = {
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
                                    importantIconOption = false
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

