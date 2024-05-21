package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import br.com.fiap.locawebmailapp.utils.returnColor
import br.com.fiap.locawebmailapp.utils.validateIfAllDay
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Stable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardAgenda(
    selectedDate: LocalDate?,
    isTask: Boolean = false,
    agenda: Agenda,
    navController: NavController,
    timePickerState: TimePickerState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp, vertical = 5.dp)
            .clickable {
                if (agenda.tarefa) {
                    navController.navigate("editatarefascreen/${agenda.id_agenda}")
                } else {
                    navController.navigate("editaeventoscreen/${agenda.id_agenda}")
                }
            },
        shape = RoundedCornerShape(3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .background(colorResource(id = R.color.lcweb_gray_1)),
            ) {

                Text(
                    text = selectedDate!!.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ).capitalize(),
                    color = colorResource(id = R.color.white),
                    fontSize = 20.sp
                )
                Text(
                    text = "${selectedDate.dayOfMonth}",
                    color = colorResource(id = R.color.white),
                    fontSize = 20.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(returnColor(option = agenda.cor))
            ) {
                Text(
                    text = agenda.nome,
                    color = colorResource(id = R.color.white),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (validateIfAllDay(agenda.horario)) {
                        Text(
                            text = stringResource(id = R.string.calendar_all_day) ,
                            color = colorResource(id = R.color.white),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(5.dp))
                    }

                    else {
                        Text(
                            text = if(timePickerState.is24hour) agenda.horario else convertTo12Hours(agenda.horario) ,
                            color = colorResource(id = R.color.white),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(5.dp))
                    }

                    if (isTask) Icon(
                        Icons.Outlined.CheckCircle, contentDescription = stringResource(id = R.string.content_desc_task), tint = colorResource(
                            id = R.color.white
                        )
                    ) else Icon(
                        Icons.Outlined.DateRange, contentDescription = stringResource(id = R.string.content_desc_event), tint = colorResource(
                            id = R.color.white
                        )
                    )
                }
            }
        }
    }
}