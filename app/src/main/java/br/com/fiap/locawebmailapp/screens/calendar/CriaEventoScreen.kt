package br.com.fiap.locawebmailapp.screens.calendar

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import br.com.fiap.locawebmailapp.components.calendar.MailOptionsDialog
import br.com.fiap.locawebmailapp.components.calendar.PeopleSelectorDalog
import br.com.fiap.locawebmailapp.components.calendar.RepeatSelectorDialog
import br.com.fiap.locawebmailapp.components.calendar.TextFieldCalendar
import br.com.fiap.locawebmailapp.components.calendar.TimeSelectorDialog
import br.com.fiap.locawebmailapp.components.general.RowIconButton
import br.com.fiap.locawebmailapp.database.repository.AgendaConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.AgendaRepository
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.utils.completeStringDateToDate
import br.com.fiap.locawebmailapp.utils.convertMillisToLocalDate
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import br.com.fiap.locawebmailapp.utils.dateToCompleteStringDate
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.localDateToMillis
import br.com.fiap.locawebmailapp.utils.returnColor
import br.com.fiap.locawebmailapp.utils.returnOneMonthFromDate
import br.com.fiap.locawebmailapp.utils.returnStringRepeatOption
import br.com.fiap.locawebmailapp.utils.stringToLocalDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Stable
@Composable
fun CriaEventoScreen(navController: NavController) {
    val taskTitle = remember {
        mutableStateOf("")
    }
    val taskDescription = remember {
        mutableStateOf("")
    }
    val allDay = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val emailRepository = EmailRepository(context)

    val timePickerState = rememberTimePickerState(
        initialHour = LocalDateTime.now().hour,
        initialMinute = LocalDateTime.now().minute
    )
    val showTimePicker = remember { mutableStateOf(false) }
    val nowTime = LocalDateTime.now()
    val patternTime = if (timePickerState.is24hour) "HH:mm" else "hh:mm a";
    val timeShow = remember {
        mutableStateOf(nowTime.format(DateTimeFormatter.ofPattern(patternTime)))
    }
    val time = remember {
        mutableStateOf(nowTime.format(DateTimeFormatter.ofPattern("HH:mm")))
    }

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker, initialSelectedDateMillis = localDateToMillis(
            LocalDate.now()
        )
    )
    val openDialogDatePicker = remember { mutableStateOf(false) }
    val timezoneFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    val millisToLocalDate = datePickerState.selectedDateMillis?.let {
        stringToLocalDate(timezoneFormatter.format(it))
    }
    val selectedDate = remember {
        mutableStateOf(dateToCompleteStringDate(LocalDate.now()))
    }

    val usuarioRepository = UsuarioRepository(LocalContext.current)
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }


    val selectedMailOption = remember {
        mutableStateOf(1)
    }

    val openDialogMailOptions = remember { mutableStateOf(false) }

    val usuariosExistentes = usuarioRepository.listarUsuarios()

    val openDialogColorPicker = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(1) }

    val openDialogRepeatPicker = remember { mutableStateOf(false) }
    val selectedRepeat = remember { mutableStateOf(1) }

    val agendaRepository = AgendaRepository(LocalContext.current)
    val convidadoRepository = ConvidadoRepository(LocalContext.current)
    val agendaConvidadoRepository = AgendaConvidadoRepository(LocalContext.current)

    val agenda = Agenda()
    val agendaConvidado = AgendaConvidado()

    val isErrorTitle = remember { mutableStateOf(false) }

    val openDialogPeoplePicker = remember { mutableStateOf(false) }

    val listConvidado = remember {
        mutableStateOf(listOf<Convidado>())
    }

    val listConvidadoSelected = remember {
        mutableStateListOf<Convidado>()
    }

    val listConvidadoText = remember {
        mutableStateOf("")
    }

    val toastMessageMailDraftSaved = stringResource(id = R.string.toast_maildraft_saved)

    val email = Email()

    listConvidado.value = convidadoRepository.listarConvidado()

    val calendarEventDraftInvite = stringResource(R.string.calendar_event_draft_invite)
    val calendarEventDraftInviteText = stringResource(R.string.calendar_event_draft_invitetext)
    val calendarEventDraftDescribeText = stringResource(R.string.calendar_event_draft_describetext)
    val calendarEventDraftDateText = stringResource(R.string.calendar_event_draft_datetext)
    val calendarEventDraftEverydayUntil =
        stringResource(R.string.calendar_event_draft_everydayuntil)
    val calendarEventDraftHourText = stringResource(R.string.calendar_event_draft_hourtext)
    val calendarEventDraftAllDay = stringResource(R.string.calendar_event_draft_allday)
    val toastMessageEventInviteSent = stringResource(id = R.string.toast_event_invitesent)


    val alteracaoRepository = AlteracaoRepository(context)



    Column {

        RowIconButton(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onClickFirstButton = {
                val previousBackStackEntry = navController.previousBackStackEntry
                if (previousBackStackEntry != null) {
                    previousBackStackEntry.savedStateHandle.set("data", LocalDate.now().toString())
                }
                navController.popBackStack()
            },
            onClickSecondButton = {
                if (taskTitle.value.isEmpty()) {
                    isErrorTitle.value = true

                } else {
                    agenda.nome = taskTitle.value
                    agenda.descritivo = taskDescription.value
                    agenda.proprietario = usuarioSelecionado.value.nome
                    agenda.data = if (millisToLocalDate.toString().equals("null")) LocalDate.now()
                        .toString() else millisToLocalDate!!.toString()
                    agenda.horario = time.value
                    agenda.cor = selectedColor.value
                    agenda.repeticao = selectedRepeat.value
                    agenda.tarefa = false
                    agenda.id_usuario = usuarioSelecionado.value.id_usuario

                    if (agenda.repeticao == 2) {
                        if (agendaRepository.listarGrupoRepeticao().isNotEmpty()) {
                            agenda.grupo_repeticao =
                                agendaRepository.listarGrupoRepeticao().last().grupo_repeticao + 1
                        }

                        for (day in returnOneMonthFromDate(agenda.data)) {
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

                    } else {
                        agendaRepository.criarAgenda(agenda)
                        agendaConvidado.id_agenda = agendaRepository.retornaValorAtualSeqPrimayKey()
                        for (convidado in listConvidadoSelected) {
                            agendaConvidado.id_convidado = convidado.id_convidado
                            agendaConvidadoRepository.criaAgendaConvidado(agendaConvidado)
                        }
                    }

                    val previousBackStackEntry = navController.previousBackStackEntry
                    if (previousBackStackEntry != null) {
                        previousBackStackEntry.savedStateHandle.set(
                            "data", if (millisToLocalDate.toString().equals("null")) LocalDate.now()
                                .toString() else millisToLocalDate!!.toString()
                        )
                    }

                    if (listConvidadoSelected.isNotEmpty()) {
                        if (selectedMailOption.value == 2) {
                            email.agenda_atrelada = true
                            email.remetente = usuarioSelecionado.value.email
                            email.destinatario =
                                listaParaString(listConvidadoSelected.map { it.email })
                            email.cc = ""
                            email.cco = ""
                            email.assunto = "Convite de agenda: ${agenda.nome}"
                            email.corpo =
                                "${agenda.proprietario} $calendarEventDraftInviteText ${agenda.nome}! \n" +
                                        "$calendarEventDraftDescribeText ${agenda.descritivo}\n" +
                                        "$calendarEventDraftDateText ${
                                            if (agenda.repeticao == 2) dateToCompleteStringDate(
                                                completeStringDateToDate(selectedDate.value)
                                            ) +
                                                    " $calendarEventDraftEverydayUntil ${
                                                        dateToCompleteStringDate(
                                                            stringToLocalDate(agenda.data)
                                                        )
                                                    }" else dateToCompleteStringDate(
                                                stringToLocalDate(
                                                    agenda.data
                                                )
                                            )
                                        }\n" +
                                        "$calendarEventDraftHourText ${

                                            if (allDay.value) {
                                                calendarEventDraftAllDay
                                            } else {
                                                if (timePickerState.is24hour) agenda.horario else convertTo12Hours(
                                                    agenda.horario
                                                )
                                            }
                                        }"
                            email.enviado = true
                            email.editavel = false
                            email.id_usuario = usuarioSelecionado.value.id_usuario
                            val rowId = emailRepository.criarEmail(email = email)
                            alteracaoRepository.criarAlteracao(
                                Alteracao(
                                    alt_id_email = rowId,
                                    alt_id_usuario = usuarioSelecionado.value.id_usuario
                                )
                            )

                            for (convidadoSelected in listConvidadoSelected) {
                                val usuarioExistente =
                                    usuarioRepository.retornaUsarioPorEmail(convidadoSelected.email)


                                if (usuarioExistente != null) {
                                    alteracaoRepository.criarAlteracao(
                                        Alteracao(
                                            alt_id_email = rowId,
                                            alt_id_usuario = usuarioExistente.id_usuario
                                        )
                                    )

                                    agenda.id_email = rowId
                                    agenda.nome = taskTitle.value
                                    agenda.descritivo = taskDescription.value
                                    agenda.proprietario = usuarioSelecionado.value.nome
                                    agenda.data = if (millisToLocalDate.toString()
                                            .equals("null")
                                    ) LocalDate.now()
                                        .toString() else millisToLocalDate!!.toString()
                                    agenda.horario = time.value
                                    agenda.cor = selectedColor.value
                                    agenda.repeticao = selectedRepeat.value
                                    agenda.tarefa = false
                                    agenda.id_usuario = usuarioExistente.id_usuario
                                    agenda.visivel = false

                                    if (agenda.repeticao == 2) {
                                        if (agendaRepository.listarGrupoRepeticao()
                                                .isNotEmpty()
                                        ) {
                                            agenda.grupo_repeticao =
                                                agendaRepository.listarGrupoRepeticao()
                                                    .last().grupo_repeticao + 1
                                        }

                                        for (day in returnOneMonthFromDate(agenda.data)) {
                                            agenda.data = day
                                            agendaRepository.criarAgenda(agenda)
                                            agendaConvidado.id_agenda =
                                                agendaRepository.retornaValorAtualSeqPrimayKey()
                                            agendaConvidado.grupo_repeticao =
                                                agenda.grupo_repeticao

                                            for (convidado in listConvidadoSelected) {
                                                agendaConvidado.id_convidado =
                                                    convidado.id_convidado
                                                agendaConvidadoRepository.criaAgendaConvidado(
                                                    agendaConvidado
                                                )
                                            }
                                        }

                                    } else {
                                        agendaRepository.criarAgenda(agenda)
                                        agendaConvidado.id_agenda =
                                            agendaRepository.retornaValorAtualSeqPrimayKey()
                                        for (convidado in listConvidadoSelected) {
                                            agendaConvidado.id_convidado =
                                                convidado.id_convidado
                                            agendaConvidadoRepository.criaAgendaConvidado(
                                                agendaConvidado
                                            )
                                        }
                                    }
                                }

                                Toast.makeText(
                                    context,
                                    toastMessageEventInviteSent,
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }

                        } else if (selectedMailOption.value == 3) {
                            email.agenda_atrelada = true
                            email.remetente = usuarioSelecionado.value.email
                            email.destinatario =
                                listaParaString(listConvidadoSelected.map { it.email })
                            email.cc = ""
                            email.cco = ""
                            email.assunto = "$calendarEventDraftInvite ${agenda.nome}"
                            email.corpo =
                                "${agenda.proprietario} $calendarEventDraftInviteText ${agenda.nome}! \n" +
                                        "$calendarEventDraftDescribeText ${agenda.descritivo}\n" +
                                        "$calendarEventDraftDateText ${
                                            if (agenda.repeticao == 2) dateToCompleteStringDate(
                                                completeStringDateToDate(selectedDate.value)
                                            ) +
                                                    " $calendarEventDraftEverydayUntil ${
                                                        dateToCompleteStringDate(
                                                            stringToLocalDate(agenda.data)
                                                        )
                                                    }" else dateToCompleteStringDate(
                                                stringToLocalDate(
                                                    agenda.data
                                                )
                                            )
                                        }\n" +
                                        "$calendarEventDraftHourText ${

                                            if (allDay.value) {
                                                calendarEventDraftAllDay
                                            } else {
                                                if (timePickerState.is24hour) agenda.horario else convertTo12Hours(
                                                    agenda.horario
                                                )
                                            }
                                        }"
                            email.enviado = false
                            email.editavel = true
                            email.id_usuario = usuarioSelecionado.value.id_usuario

                            emailRepository.criarEmail(email = email)

                            Toast.makeText(context, toastMessageMailDraftSaved, Toast.LENGTH_LONG)
                                .show()

                        }
                    }
                    navController.popBackStack()
                }
            },
            iconFirstButton = Icons.Filled.Close,
            textSecondButton = stringResource(id = R.string.calendar_save_button),
            descriptionFirstButton = "",
            colorsSecondButton = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.lcweb_red_1)
            ),
            colorSecondTextButton = colorResource(id = R.color.white)
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
            isReadOnly = false,
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
                    if (usuarioSelecionado.value.nome.length > 25) {
                        "${
                            usuarioSelecionado.value.nome.take(
                                25
                            )
                        }..."
                    } else {
                        usuarioSelecionado.value.nome
                    }
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
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(5.dp)
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
                listConvidado = listConvidado,
                listConvidadoText = listConvidadoText,
                listConvidadoSelected = listConvidadoSelected,
                usuarioSelecionado = usuarioSelecionado
            )
        }

        if (listConvidadoSelected.isNotEmpty()) {
            Button(
                onClick = {
                    openDialogMailOptions.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    painter = painterResource(id = R.drawable.envelope_open_text_solid),
                    contentDescription = stringResource(id = R.string.content_desc_mail_options),
                    tint = colorResource(id = R.color.lcweb_gray_1)
                )

                Text(
                    text = stringResource(id = R.string.calendar_event_mail_options),
                    color = colorResource(id = R.color.lcweb_gray_1),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                MailOptionsDialog(
                    openDialogMailOptions = openDialogMailOptions,
                    selectedMailOption = selectedMailOption
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
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
                    uncheckedTrackColor = colorResource(id = R.color.lcweb_gray_1)
                )
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
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(5.dp)
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
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(5.dp)
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
                containerColor = Color.Transparent
            )
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
            }

            ColorSelectorDalog(
                openDialogColorPicker = openDialogColorPicker,
                selectedColor = selectedColor
            )
        }

        Button(
            onClick = { openDialogRepeatPicker.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
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
            isReadOnly = false,
            maxLines = 3,
            isError = false
        )
    }
}