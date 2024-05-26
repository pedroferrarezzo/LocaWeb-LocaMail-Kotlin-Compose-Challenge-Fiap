package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.AgendaCor
import br.com.fiap.locawebmailapp.utils.returnColor
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

@Stable
@Composable
fun Day(
    day: CalendarDay,
    selectedDate: Boolean,
    listColorTask: List<AgendaCor>,
    onClick: (CalendarDay) -> Unit) {

    Box(
        modifier = Modifier
            .padding(1.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                color = if (day.date == LocalDate.now() && day.position == DayPosition.MonthDate) colorResource(id = R.color.lcweb_gray_1)
                else if (selectedDate && day.position == DayPosition.MonthDate) colorResource(
                    id = R.color.lcweb_gray_2
                )
                else if (day.position != DayPosition.MonthDate) Color.Transparent
                else Color.Transparent
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color =
            if (day.position == DayPosition.MonthDate && (day.date != LocalDate.now()) && !selectedDate) colorResource(
                id = R.color.lcweb_gray_1
            )
            else if(day.position != DayPosition.MonthDate) Color.Transparent
            else if (day.date == LocalDate.now()) colorResource(id = R.color.lcweb_red_1)
            else if (selectedDate) colorResource(id = R.color.white)
            else Color.Transparent
        )

        if (listColorTask.isNotEmpty() && day.position == DayPosition.MonthDate) {
            LazyRow(
                content = {
                    items(
                        listColorTask,
                        key = {
                            it.cor
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(7.dp)
                                .height(7.dp)
                                .padding(2.dp)
                                .background(returnColor(option = it.cor))

                        )
                    }

                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
            )
        }
    }
}