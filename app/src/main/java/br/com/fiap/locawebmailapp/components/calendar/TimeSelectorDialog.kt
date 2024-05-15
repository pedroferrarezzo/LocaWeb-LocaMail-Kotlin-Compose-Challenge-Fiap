package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.utils.convertTo12Hours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectorDialog(
    showTimePicker: MutableState<Boolean>,
    time: MutableState<String>,
    timeShow: MutableState<String>,
    timePickerState: TimePickerState

) {
    if (showTimePicker.value) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        time.value = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                        timeShow.value = if(timePickerState.is24hour) time.value else convertTo12Hours(time.value)
                        showTimePicker.value = false
                    }
                ) { Text(stringResource(id = R.string.calendar_ok_button), color = colorResource(id = R.color.lcweb_red_1)) }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker.value = false
                    }
                ) { Text(stringResource(id = R.string.calendar_cancel_button), color = colorResource(id = R.color.lcweb_red_1)) }
            }
        )
        {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    selectorColor = colorResource(id = R.color.lcweb_red_1),
                    clockDialColor = colorResource(id = R.color.lcweb_gray_1),
                    clockDialUnselectedContentColor = colorResource(id = R.color.white),
                    periodSelectorBorderColor = Color.Transparent,
                    periodSelectorSelectedContainerColor = colorResource(id = R.color.lcweb_red_4),
                    periodSelectorSelectedContentColor = colorResource(id = R.color.lcweb_gray_1),
                    periodSelectorUnselectedContentColor = colorResource(id = R.color.lcweb_gray_1),
                    timeSelectorSelectedContentColor = colorResource(id = R.color.lcweb_gray_1),
                    timeSelectorUnselectedContentColor = colorResource(id = R.color.lcweb_gray_1),
                    timeSelectorSelectedContainerColor = colorResource(id = R.color.lcweb_red_4)
                ))
        }
    }

}