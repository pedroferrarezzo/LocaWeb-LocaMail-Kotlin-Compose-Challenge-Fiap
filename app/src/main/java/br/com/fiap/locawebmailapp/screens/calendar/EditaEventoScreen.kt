package br.com.fiap.locawebmailapp.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.calendar.ColorSelectorDalog
import br.com.fiap.locawebmailapp.components.calendar.DateSelectorDialog
import br.com.fiap.locawebmailapp.components.calendar.PeopleSelectorDalog
import br.com.fiap.locawebmailapp.components.calendar.RepeatSelectorDialog
import br.com.fiap.locawebmailapp.components.calendar.RowTwoIconCondition
import br.com.fiap.locawebmailapp.components.calendar.TextFieldCalendar
import br.com.fiap.locawebmailapp.components.calendar.TimeSelectorDialog
import br.com.fiap.locawebmailapp.database.repository.AgendaConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.AgendaRepository
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.IdConvidado
import br.com.fiap.locawebmailapp.utils.convertMillisToLocalDate
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import br.com.fiap.locawebmailapp.utils.localDateToMillis
import br.com.fiap.locawebmailapp.utils.returnColor
import br.com.fiap.locawebmailapp.utils.returnHourAndMinuteSeparate
import br.com.fiap.locawebmailapp.utils.returnListConvidado
import br.com.fiap.locawebmailapp.utils.returnOneMonthFromDate
import br.com.fiap.locawebmailapp.utils.returnStringRepeatOption
import br.com.fiap.locawebmailapp.utils.stringToDate
import br.com.fiap.locawebmailapp.utils.stringToLocalDate
import br.com.fiap.locawebmailapp.utils.validateIfAllDay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

@Stable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditaEventoScreen(navController: NavController, id_agenda: Int) {

    val agendaRepository = AgendaRepository(LocalContext.current)
    val agenda = agendaRepository.listarAgendaPorId(id_agenda)
    val agendaConvidado = AgendaConvidado()

    val convidadoRepository = ConvidadoRepository(LocalContext.current)
    val agendaConvidadoRepository = AgendaConvidadoRepository(LocalContext.current)

    val isEdit = remember {
        mutableStateOf(false)
    }

    val usuarioRepository = UsuarioRepository(LocalContext.current)
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val taskTitle = remember {
        mutableStateOf(agenda.nome)
    }
    val taskDescription = remember {
        mutableStateOf(agenda.descritivo)
    }
    val allDay = remember {
        mutableStateOf(validateIfAllDay(agenda.horario))
    }

    val timePickerState = rememberTimePickerState(
        initialHour = if (agenda != null && agenda.horario != "1") returnHourAndMinuteSeparate(
            agenda.horario
        ).first() else LocalDateTime.now().hour,
        initialMinute = if (agenda != null && agenda.horario != "1") returnHourAndMinuteSeparate(
            agenda.horario
        ).last() else LocalDateTime.now().minute
    )

    val showTimePicker = remember { mutableStateOf(false) }

    val timeShow = remember {
        mutableStateOf(if (timePickerState.is24hour) agenda.horario else convertTo12Hours(agenda.horario))
    }
    val time = remember {
        mutableStateOf(timeShow.value)
    }

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = if (agenda != null) localDateToMillis(stringToLocalDate(agenda.data)) else 0
    )
    val openDialogDatePicker = remember { mutableStateOf(false) }
    val timezoneFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    val millisToLocalDate = datePickerState.selectedDateMillis?.let {
        stringToLocalDate(timezoneFormatter.format(it))
    }
    val selectedDate = remember {
        mutableStateOf(stringToDate(agenda.data))
    }

    val openDialogColorPicker = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(agenda.cor) }

    val openDialogRepeatPicker = remember { mutableStateOf(false) }
    val selectedRepeat = remember { mutableStateOf(agenda.repeticao) }

    val isErrorTitle = remember { mutableStateOf(false) }

    val openDialogPeoplePicker = remember { mutableStateOf(false) }

    val listTodoConvidado = remember {
        mutableStateOf(listOf<Convidado>())
    }

    listTodoConvidado.value = convidadoRepository.listarConvidado()

    val listIdConvidado = remember {
        agendaConvidadoRepository.listarIdConvidadoPorAgenda(agenda.id_agenda)
    }

    val listConvidadoText = remember {
        mutableStateOf("")
    }

    val listConvidadoSelected = remember {
        returnListConvidado(
            listIdConvidado = listIdConvidado,
            convidadoRepository = convidadoRepository
        )
    }

    val initialDate = if (agenda != null) agenda.data else ""


    Box() {

        Column {
            RowTwoIconCondition(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClickFirstButton = {
                    val previousBackStackEntry = navController.previousBackStackEntry
                    if (previousBackStackEntry != null) {
                        previousBackStackEntry.savedStateHandle.set("data", agenda.data)
                    }
                    navController.popBackStack()
                },
                onClickSecondButton = {
                    isEdit.value = true
                },
                iconFirstButton = Icons.Filled.Close,
                iconSecondButton = Icons.Filled.Edit,
                descriptionFirstButton = "",
                descriptionSecondButton = "",
                textSecondButtonText = stringResource(id = R.string.calendar_update_button),
                onClickSecondButtonText = {

                    if (taskTitle.value.isEmpty()) {
                        isErrorTitle.value = true

                    } else {
                        agenda.nome = taskTitle.value
                        agenda.descritivo = taskDescription.value
                        agenda.horario = time.value
                        agenda.cor = selectedColor.value
                        agenda.data =
                            if (millisToLocalDate.toString().equals("null")) LocalDate.now()
                                .toString() else millisToLocalDate!!.toString()

                        if (selectedRepeat.value == agenda.repeticao) {

                            agenda.repeticao = selectedRepeat.value
                            agendaConvidado.id_agenda = agenda.id_agenda
                            agendaRepository.atualizaAgenda(agenda)

                            for (convidado in listConvidadoSelected) {
                                if (!(listIdConvidado.contains(IdConvidado(convidado.id_convidado)))) {
                                    agendaConvidado.id_convidado = convidado.id_convidado
                                    agendaConvidadoRepository.criaAgendaConvidado(agendaConvidado)
                                }
                            }

                            for (id in listIdConvidado) {
                                if (!(listConvidadoSelected.any {
                                        it.id_convidado == id.id_convidado
                                    })) {
                                    agendaConvidadoRepository.excluirPorIdAgendaEIdConvidado(
                                        id_agenda = agenda.id_agenda,
                                        id_convidado = id.id_convidado
                                    )
                                }
                            }
                        } else if (agenda.repeticao == 2 && selectedDate.value != stringToDate(
                                initialDate
                            )
                        ) {

                            agendaRepository.excluirPorGrupoRepeticao(
                                agenda.grupo_repeticao
                            )

                            for (day in returnOneMonthFromDate(agenda.data)) {
                                agenda.id_agenda = 0
                                agenda.data = day
                                agendaRepository.criarAgenda(agenda)
                                agendaConvidado.id_agenda =
                                    agendaRepository.retornaValorAtualSeqPrimayKey()
                                agendaConvidado.grupo_repeticao = agenda.grupo_repeticao

                                for (convidado in listConvidadoSelected) {
                                    agendaConvidado.id_convidado = convidado.id_convidado
                                    agendaConvidadoRepository.criaAgendaConvidado(agendaConvidado)
                                }
                            }
                        } else if (selectedRepeat.value == 1 && agenda.repeticao == 2) {

                            agendaConvidadoRepository.excluirPorGrupoRepeticaoExcetoIdAgenda(
                                grupo_repeticao = agenda.grupo_repeticao,
                                id_agenda = agenda.id_agenda
                            )

                            agendaRepository.excluirPorGrupoRepeticaoExcetoData(
                                agenda.grupo_repeticao,
                                agenda.data
                            )
                            agendaRepository.atualizaOpcaoRepeticaoPorGrupoRepeticao(
                                agenda.grupo_repeticao,
                                1
                            )
                        } else if (selectedRepeat.value == 2 && agenda.repeticao == 1 && selectedDate.value != agenda.data) {
                            agendaRepository.excluiAgenda(agenda)

                            for (day in returnOneMonthFromDate(agenda.data)) {
                                agenda.repeticao = 2
                                agenda.id_agenda = 0
                                agenda.data = day
                                agendaRepository.criarAgenda(agenda)
                                agendaConvidado.id_agenda =
                                    agendaRepository.retornaValorAtualSeqPrimayKey()
                                agendaConvidado.grupo_repeticao = agenda.grupo_repeticao

                                for (convidado in listConvidadoSelected) {
                                    agendaConvidado.id_convidado = convidado.id_convidado
                                    agendaConvidadoRepository.criaAgendaConvidado(agendaConvidado)
                                }
                            }
                        } else if (selectedRepeat.value == 2 && agenda.repeticao == 1) {
                            agenda.repeticao = 2
                            agendaRepository.atualizaOpcaoRepeticaoPorIdAgenda(agenda.id_agenda, 2)

                            for (day in returnOneMonthFromDate(agenda.data)) {
                                if (agenda.data != day) {
                                    agenda.id_agenda = 0
                                    agenda.data = day
                                    agendaRepository.criarAgenda(agenda)
                                    agendaConvidado.id_agenda =
                                        agendaRepository.retornaValorAtualSeqPrimayKey()
                                    agendaConvidado.grupo_repeticao = agenda.grupo_repeticao

                                    for (convidado in listConvidadoSelected) {
                                        agendaConvidado.id_convidado = convidado.id_convidado
                                        agendaConvidadoRepository.criaAgendaConvidado(
                                            agendaConvidado
                                        )
                                    }
                                }
                            }
                        }
                        val previousBackStackEntry = navController.previousBackStackEntry
                        if (previousBackStackEntry != null) {
                            previousBackStackEntry.savedStateHandle.set(
                                "data",
                                if (millisToLocalDate.toString().equals("null")) LocalDate.now()
                                    .toString() else millisToLocalDate!!.toString()
                            )
                        }
                        navController.popBackStack()
                    }
                },
                colorText = colorResource(id = R.color.white),
                colorsSecondButtonText = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.lcweb_red_1)
                ),
                isEdit = isEdit.value
            )

            TextFieldCalendar(
                value = taskTitle.value,
                onValueChange = {
                    isErrorTitle.value = it.isEmpty()
                    taskTitle.value = it
                },
                placeholderText = stringResource(id = R.string.calendar_event_placeholder_title),
                fontSizePlaceHolder = 25.sp,
                fontSizeTextStyle = 25.sp,
                paddingTextField = 20.dp,
                descriptionTrailingIcon = "",
                iconTrailingIcon = Icons.Outlined.DateRange,
                isReadOnly = !isEdit.value,
                maxLines = 2,
                isError = isErrorTitle.value
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(id = R.string.content_desc_user),
                    tint = colorResource(id = R.color.lcweb_gray_1)
                )

                Text(
                    text = "${stringResource(id = R.string.calendar_organizer_text)}: ${
                        if (agenda != null)
                            if (agenda.proprietario.length > 25) {
                                "${
                                    agenda.proprietario.take(
                                        25
                                    )
                                }..."
                            } else {
                                agenda.proprietario
                            }
                        else ""
                    }",
                    modifier = Modifier.padding(5.dp),
                    color = colorResource(id = R.color.lcweb_gray_1),
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = {
                    openDialogPeoplePicker.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                ),
                shape = RoundedCornerShape(5.dp),
                enabled = isEdit.value

            ) {
                Icon(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    painter = painterResource(id = R.drawable.user_group),
                    contentDescription = stringResource(id = R.string.content_desc_group_user),
                    tint = colorResource(id = R.color.lcweb_gray_1)
                )

                Text(
                    text = stringResource(id = R.string.calendar_event_participants),
                    color = colorResource(id = R.color.lcweb_gray_1),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                PeopleSelectorDalog(
                    openDialogPeoplePicker = openDialogPeoplePicker,
                    listConvidado = listTodoConvidado,
                    listConvidadoText = listConvidadoText,
                    listConvidadoSelected = listConvidadoSelected,
                    usuarioSelecionado = usuarioSelecionado
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        painter = painterResource(id = R.drawable.clock_solid),
                        contentDescription = stringResource(id = R.string.content_desc_clock),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )

                    Text(
                        text = stringResource(id = R.string.calendar_all_day),
                        modifier = Modifier.padding(5.dp),
                        color = colorResource(id = R.color.lcweb_gray_1),
                        fontSize = 20.sp
                    )
                }

                Switch(
                    checked = allDay.value,
                    onCheckedChange = {
                        time.value = if (!allDay.value) "1" else LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern(if (timePickerState.is24hour) "HH:mm" else "hh:mm a"))
                        timeShow.value = time.value
                        allDay.value = !allDay.value
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.white),
                        uncheckedThumbColor = colorResource(id = R.color.white),
                        uncheckedBorderColor = colorResource(id = R.color.lcweb_gray_1),
                        checkedBorderColor = colorResource(id = R.color.lcweb_red_1),
                        checkedTrackColor = colorResource(id = R.color.lcweb_red_1),
                        uncheckedTrackColor = colorResource(id = R.color.lcweb_gray_1),
                        disabledCheckedTrackColor = colorResource(id = R.color.lcweb_red_1),
                        disabledUncheckedTrackColor = colorResource(id = R.color.lcweb_gray_1),
                        disabledCheckedThumbColor = colorResource(id = R.color.lcweb_gray_3),
                        disabledUncheckedThumbColor = colorResource(id = R.color.lcweb_gray_3)
                    ),
                    enabled = isEdit.value
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        openDialogDatePicker.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                    ),
                    shape = RoundedCornerShape(5.dp),
                    enabled = isEdit.value
                ) {
                    Text(
                        text = selectedDate.value,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        fontSize = 20.sp
                    )

                    DateSelectorDialog(
                        openDialogDatePicker = openDialogDatePicker,
                        selectedDate = selectedDate,
                        millisToLocalDate = millisToLocalDate,
                        datePickerState = datePickerState
                    )
                }

                if (!allDay.value) {
                    Button(
                        onClick = {
                            showTimePicker.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                        ),
                        shape = RoundedCornerShape(5.dp),
                        enabled = isEdit.value,

                        ) {
                        Text(
                            text = timeShow.value,
                            color = colorResource(id = R.color.lcweb_gray_1),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )

                        TimeSelectorDialog(
                            showTimePicker = showTimePicker,
                            time = time,
                            timeShow = timeShow,
                            timePickerState = timePickerState
                        )
                    }
                }
            }

            Button(
                onClick = { openDialogColorPicker.value = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                ),
                enabled = isEdit.value
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(30.dp)
                            .height(30.dp)
                            .background(returnColor(option = selectedColor.value))
                    )
                    Text(
                        text = stringResource(id = R.string.calendar_choose_color),
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)
                    )

                    ColorSelectorDalog(
                        openDialogColorPicker = openDialogColorPicker,
                        selectedColor = selectedColor
                    )
                }

                ColorSelectorDalog(
                    openDialogColorPicker = openDialogColorPicker,
                    selectedColor = selectedColor
                )
            }

            Button(
                onClick = { openDialogRepeatPicker.value = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                ),
                enabled = isEdit.value
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = stringResource(id = R.string.content_desc_calendar_repeat),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                    Text(
                        text = returnStringRepeatOption(selectedRepeat.value),
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)
                    )
                }

                RepeatSelectorDialog(
                    openDialogRepeatPicker = openDialogRepeatPicker,
                    selectedRepeat = selectedRepeat
                )
            }

            TextFieldCalendar(
                value = taskDescription.value,
                onValueChange = {
                    taskDescription.value = it
                },
                placeholderText = stringResource(id = R.string.calendar_event_description_text),
                fontSizePlaceHolder = 20.sp,
                fontSizeTextStyle = 20.sp,
                paddingTextField = 20.dp,
                descriptionTrailingIcon = "",
                iconTrailingIcon = Icons.Outlined.Info,
                isReadOnly = !isEdit.value,
                maxLines = 4,
                isError = false
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                agendaConvidadoRepository.excluirPorIdAgenda(agenda.id_agenda)

                agendaRepository.excluiAgenda(agenda)
                val previousBackStackEntry = navController.previousBackStackEntry
                if (previousBackStackEntry != null) {
                    previousBackStackEntry.savedStateHandle.set("data", agenda.data)
                }
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.lcweb_red_1)
            ),
            shape = RectangleShape
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.calendar_event_mark))
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = stringResource(id = R.string.content_desc_event)
                )
            }

        }
    }
}