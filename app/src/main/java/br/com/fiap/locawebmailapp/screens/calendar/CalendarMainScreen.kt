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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.calendar.Calendar
import br.com.fiap.locawebmailapp.components.calendar.CardAgenda
import br.com.fiap.locawebmailapp.components.calendar.Day
import br.com.fiap.locawebmailapp.components.calendar.ExpandedShadowDropdown
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.components.general.ShadowBox
import br.com.fiap.locawebmailapp.database.repository.AgendaRepository
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaCor
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Stable
@Composable
fun CalendarMainScreen(navController: NavController) {
    val expanded = remember {
        mutableStateOf(false)
    }

    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val listTask = remember {
        mutableStateOf(listOf<Agenda>())
    }

    var listColorTask = listOf<AgendaCor>()

    val context = LocalContext.current
    val agendaRepository = remember {
        AgendaRepository(context)
    }

    val onClickDay = remember {
        { day: CalendarDay ->
            selectedDate = day.date
        }
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

    listTask.value = agendaRepository.listarAgendaPorDia(selectedDate.toString())

    ModalNavDrawer(
        selectedDrawer = selectedDrawer,
        navController = navController,
        drawerState = drawerState
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Column {

                IconButton(modifier = Modifier
                    .width(45.dp).height(45.dp)
                    .padding(5.dp),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                    Icon(
                        modifier = Modifier.width(45.dp).height(45.dp),
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "",
                        tint = colorResource(
                            id = R.color.lcweb_gray_1
                        )
                    )
                }

                Calendar(
                    stateHorizontalCalendar = stateHorizontalCalendar,
                    daysOfWeek = daysOfWeek,
                    dayContent = { day ->
                        listColorTask = agendaRepository.listarCorAgendaPorDia(day.date.toString())

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
                            CardAgenda(
                                selectedDate = selectedDate,
                                isTask = it.tarefa,
                                agenda = it,
                                navController = navController,
                                timePickerState = rememberTimePickerState()
                            )
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
                        contentDescription = "",
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}
