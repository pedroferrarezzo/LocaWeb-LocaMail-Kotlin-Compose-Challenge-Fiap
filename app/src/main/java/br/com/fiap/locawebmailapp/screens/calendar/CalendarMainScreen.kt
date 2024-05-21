package br.com.fiap.locawebmailapp.screens.calendar

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.calendar.Calendar
import br.com.fiap.locawebmailapp.components.calendar.CardAgenda
import br.com.fiap.locawebmailapp.components.calendar.Day
import br.com.fiap.locawebmailapp.components.calendar.ExpandedShadowDropdown
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.components.general.ShadowBox
import br.com.fiap.locawebmailapp.components.user.UserSelectorDalog
import br.com.fiap.locawebmailapp.database.repository.AgendaRepository
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaCor
import br.com.fiap.locawebmailapp.model.Pasta
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Stable
@Composable
fun CalendarMainScreen(navController: NavController, data: LocalDate = LocalDate.now()) {
    val expanded = remember {
        mutableStateOf(false)
    }

    var selectedDate by remember { mutableStateOf<LocalDate?>(data) }
    val listTask = remember {
        mutableStateOf(listOf<Agenda>())
    }

    var listColorTask = listOf<AgendaCor>()

    val textSearchBar = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val agendaRepository = remember {
        AgendaRepository(context)
    }

    val onClickDay = remember {
        { day: CalendarDay ->
            selectedDate = day.date
        }
    }

    val usuarioRepository = UsuarioRepository(LocalContext.current)
    val pastaRepository = PastaRepository(LocalContext.current)
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val expandedPasta = remember {
        mutableStateOf(true)
    }

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val daysOfWeek = remember { daysOfWeek() }
    val stateHorizontalCalendar = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    val selectedDrawer = remember {
        mutableStateOf("6")
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    listTask.value = agendaRepository.listarAgendaPorDia(
        selectedDate.toString(),
        usuarioSelecionado.value.id_usuario
    )

    val openDialogPastaCreator = remember {
        mutableStateOf(false)
    }

    val textPastaCreator = remember {
        mutableStateOf("")
    }

    val usuariosExistentes = usuarioRepository.listarUsuariosNaoSelecionados()

    val openDialogUserPicker = remember {
        mutableStateOf(false)
    }

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

    val alteracaoRepository = AlteracaoRepository(context)

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

        Box(modifier = Modifier.fillMaxSize()) {
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
                                text = stringResource(id = R.string.calendar_main_searchbar)
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


                UserSelectorDalog<Agenda>(
                    openDialogUserPicker = openDialogUserPicker,
                    usuarioSelecionado,
                    usuariosExistentes,
                    usuarioRepository = usuarioRepository
                )

                Calendar(
                    stateHorizontalCalendar = stateHorizontalCalendar,
                    daysOfWeek = daysOfWeek,
                    dayContent = { day ->
                        listColorTask = agendaRepository.listarCorAgendaPorDia(
                            day.date.toString(),
                            usuarioSelecionado.value.id_usuario
                        )

                        Day(
                            day = day,
                            selectedDate = selectedDate == day.date,
                            listColorTask = listColorTask,
                            onClick = onClickDay
                        )
                    })

                LazyColumn(
                    reverseLayout = true,
                    content = {
                        items(
                            listTask.value,
                            key = {
                                it.id_agenda
                            }
                        ) {
                            if (
                                it.nome.contains(textSearchBar.value, ignoreCase = true) ||
                                it.descritivo.contains(textSearchBar.value, ignoreCase = true)
                            ) {
                                CardAgenda(
                                    selectedDate = selectedDate,
                                    isTask = it.tarefa,
                                    agenda = it,
                                    navController = navController,
                                    timePickerState = rememberTimePickerState()
                                )
                            }
                        }
                    }
                )
            }


            ShadowBox(expanded = expanded)

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd)
            ) {
                ExpandedShadowDropdown(navController = navController, expanded = expanded)

                Button(
                    onClick = { expanded.value = true },
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lcweb_red_1)),
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.content_desc_add),
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}
