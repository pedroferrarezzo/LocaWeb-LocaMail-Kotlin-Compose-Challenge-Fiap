package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import br.com.fiap.locawebmailapp.R
import com.kizitonwose.calendar.core.CalendarMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Stable
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>, calendarMonth: CalendarMonth) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = if (LocalDate.now().dayOfWeek == dayOfWeek && YearMonth.now() == calendarMonth.yearMonth) colorResource(id = R.color.lcweb_red_1) else colorResource(id = R.color.lcweb_gray_1)
            )
        }
    }
}