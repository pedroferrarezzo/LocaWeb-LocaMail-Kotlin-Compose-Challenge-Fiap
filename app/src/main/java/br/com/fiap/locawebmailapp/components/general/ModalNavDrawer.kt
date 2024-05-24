package br.com.fiap.locawebmailapp.components.general

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.PastaCreatorDalog
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Pasta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ModalNavDrawer(
    selectedDrawer: MutableState<String>,
    selectedDrawerPasta: MutableState<String>,
    navController: NavController,
    drawerState: DrawerState,
    scrollState: ScrollState,
    usuarioRepository: UsuarioRepository,
    pastaRepository: PastaRepository,
    alteracaoRepository: AlteracaoRepository,
    expandedPasta: MutableState<Boolean>,
    openDialogPastaCreator: MutableState<Boolean>,
    textPastaCreator: MutableState<String>,
    listPastaState: SnapshotStateList<Pasta>,
    receivedEmailStateListRecompose: () -> Unit = {},
    context: Context,
    toastMessageFolderDeleted: String,
    scope: CoroutineScope,
    content: @Composable () -> Unit

) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(250.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.locaweb),
                    contentDescription = stringResource(id = R.string.content_desc_lcweb_logo),
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)

                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item() {

                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ai_mi_algorithm_svgrepo_com),
                                        contentDescription = stringResource(id = R.string.content_desc_algorithm),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_ai),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "11",
                            onClick = {

                                if (navController.currentBackStackEntry?.destination?.route != "aiscreen") {
                                    selectedDrawer.value = "11"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("aiscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )


                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Email,
                                        contentDescription = stringResource(id = R.string.content_desc_mail_box),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_all_accounts),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "1",
                            onClick = {

                                if (navController.currentBackStackEntry?.destination?.route != "emailtodascontasscreen") {
                                    selectedDrawer.value = "1"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailtodascontasscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )


                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.inbox_solid),
                                        contentDescription = stringResource(id = R.string.content_desc_inbox),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_main),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "2",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailmainscreen") {
                                    selectedDrawer.value = "2"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailmainscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )

                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Send,
                                        contentDescription = stringResource(id = R.string.content_desc_mail_send),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_sent),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "3",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailsenviadosscreen") {
                                    selectedDrawer.value = "3"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailsenviadosscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )

                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = stringResource(id = R.string.content_desc_favorite),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_favorites),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "4",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailsfavoritosscreen") {
                                    selectedDrawer.value = "4"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailsfavoritosscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )



                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.users_solid),
                                        contentDescription = stringResource(id = R.string.content_desc_users),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_social),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "10",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailssociaisscreen") {
                                    selectedDrawer.value = "10"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailssociaisscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )

                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.pen_to_square_solid),
                                        contentDescription = stringResource(id = R.string.content_desc_drafts),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_drafts),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "5",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailseditaveisscreen") {
                                    selectedDrawer.value = "5"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailseditaveisscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )

                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.calendar_days_regular),
                                        contentDescription = stringResource(id = R.string.content_desc_calendar),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_calendar),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "6",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "calendarmainscreen") {
                                    selectedDrawer.value = "6"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("calendarmainscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )

                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.folder_open_solid),
                                        contentDescription = stringResource(id = R.string.content_desc_folder),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_archive),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "7",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailsarquivadosscreen") {
                                    selectedDrawer.value = "7"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailsarquivadosscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )

                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.exclamation_mark_filled),
                                        contentDescription = stringResource(id = R.string.content_desc_spam),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_spam),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "9",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailsspamscreen") {
                                    selectedDrawer.value = "9"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailsspamscreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )



                        NavigationDrawerItem(
                            modifier = Modifier.padding(end = 5.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 50,
                                topEndPercent = 50
                            ),
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = stringResource(id = R.string.content_desc_trash),
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.navbar_modal_bin),
                                        modifier = Modifier.padding(start = 5.dp),
                                        fontSize = 15.sp
                                    )
                                }
                            },
                            selected = selectedDrawer.value == "8",
                            onClick = {
                                if (navController.currentBackStackEntry?.destination?.route != "emailslixeirascreen") {
                                    selectedDrawer.value = "8"
                                    selectedDrawerPasta.value = ""
                                    navController.navigate("emailslixeirascreen")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.navbar_modal_folders),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (listPastaState.isNotEmpty()) {
                                    IconButton(onClick = {
                                        expandedPasta.value = !expandedPasta.value
                                    }) {
                                        Icon(
                                            imageVector = if (!expandedPasta.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                            contentDescription = stringResource(id = R.string.content_desc_expand_area),
                                            tint = colorResource(id = R.color.lcweb_gray_1)
                                        )
                                    }
                                }

                                IconButton(onClick = {
                                    openDialogPastaCreator.value = true

                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = stringResource(id = R.string.content_desc_add),
                                        tint = colorResource(id = R.color.lcweb_gray_1)
                                    )
                                }
                            }
                        }

                        PastaCreatorDalog(
                            openDialogPastaCreator = openDialogPastaCreator,
                            usuarioRepository = usuarioRepository,
                            pastaRepository = pastaRepository,
                            textPastaCreator = textPastaCreator,
                            listPastaState = listPastaState
                        )
                    }

                    if (listPastaState.isNotEmpty()) {

                        if (expandedPasta.value) {

                            items(listPastaState) {
                                NavigationDrawerItem(
                                    modifier = Modifier.padding(end = 5.dp),
                                    shape = RoundedCornerShape(
                                        bottomEndPercent = 50,
                                        topEndPercent = 50
                                    ),
                                    label = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.box_archive_solid),
                                                    contentDescription = stringResource(id = R.string.content_desc_box_archived),
                                                    modifier = Modifier
                                                        .width(15.dp)
                                                        .height(15.dp)
                                                )
                                                Text(
                                                    text = it.nome,
                                                    modifier = Modifier.padding(start = 5.dp),
                                                    fontSize = 15.sp
                                                )
                                            }

                                            IconButton(onClick = {
                                                Toast.makeText(
                                                    context,
                                                    toastMessageFolderDeleted,
                                                    Toast.LENGTH_LONG
                                                )
                                                    .show()

                                                for (alteracao in alteracaoRepository.listarAlteracaoPorIdUsuarioEIdPasta(
                                                    id_usuario = it.id_usuario,
                                                    id_pasta = it.id_pasta
                                                )) {
                                                    alteracaoRepository.atualizarPastaPorIdAlteracao(
                                                        pasta = null,
                                                        id_alteracao = alteracao.id_alteracao
                                                    )
                                                }


                                                if (selectedDrawerPasta.value != "") {
                                                    selectedDrawerPasta.value = ""
                                                    navController.popBackStack()
                                                }

                                                receivedEmailStateListRecompose()
                                                pastaRepository.excluirPasta(it)
                                                listPastaState.remove(it)
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Filled.Clear,
                                                    contentDescription = stringResource(id = R.string.content_desc_clear),
                                                    tint = if (selectedDrawerPasta.value == it.id_pasta.toString()) colorResource(
                                                        id = R.color.lcweb_red_1
                                                    ) else colorResource(id = R.color.lcweb_gray_1),
                                                    modifier = Modifier
                                                        .width(15.dp)
                                                        .height(15.dp)
                                                )
                                            }
                                        }
                                    },
                                    selected = selectedDrawerPasta.value == it.id_pasta.toString(),
                                    onClick = {
                                        if (selectedDrawerPasta.value != it.id_pasta.toString()) {
                                            selectedDrawerPasta.value = it.id_pasta.toString()
                                            selectedDrawer.value = ""
                                            navController.navigate("emailspastascreen/${it.id_pasta}")
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        }
                                    },
                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        },
        content = content
    )
}