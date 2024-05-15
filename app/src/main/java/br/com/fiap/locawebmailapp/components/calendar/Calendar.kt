package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.core.CalendarDay
import java.time.DayOfWeek

@Stable
@Composable
fun Calendar(
    stateHorizontalCalendar: CalendarState,
    daysOfWeek: List<DayOfWeek>,
    dayContent: @Composable() (BoxScope.(CalendarDay) -> Unit)
) {
    Box(
    ) {
        HorizontalCalendar(
            state = stateHorizontalCalendar,
            dayContent = dayContent,
            monthHeader = { month ->
                MonthHeader(month)
                DaysOfWeekTitle(daysOfWeek = daysOfWeek, month)
            },
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
        )

    }
}