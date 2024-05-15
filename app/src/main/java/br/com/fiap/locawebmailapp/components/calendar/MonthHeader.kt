package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.utils.displayText
import com.kizitonwose.calendar.core.CalendarMonth

@Stable
@Composable
fun MonthHeader(calendarMonth: CalendarMonth) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = calendarMonth.yearMonth.displayText().capitalize(),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.lcweb_red_1)
        )
    }
}