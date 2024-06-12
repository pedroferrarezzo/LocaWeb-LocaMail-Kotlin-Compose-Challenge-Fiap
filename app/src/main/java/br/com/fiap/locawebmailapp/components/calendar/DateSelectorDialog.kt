package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.utils.dateToCompleteStringDate
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorDialog(
    openDialogDatePicker: MutableState<Boolean>,
    selectedDate: MutableState<String>,
    millisToLocalDate: LocalDate?,
    datePickerState: DatePickerState
) {

    if (openDialogDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialogDatePicker.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialogDatePicker.value = false
                        selectedDate.value = millisToLocalDate?.let {
                            dateToCompleteStringDate(millisToLocalDate)
                        } ?: ""
                    }

                ) {
                    Text(stringResource(id = R.string.calendar_ok_button), color = colorResource(id = R.color.lcweb_red_1))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialogDatePicker.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.calendar_cancel_button), color = colorResource(id = R.color.lcweb_red_1))
                }
            }
        ) {

            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = colorResource(id = R.color.lcweb_red_1),
                    selectedYearContainerColor = colorResource(id = R.color.lcweb_red_1),
                    todayDateBorderColor = colorResource(id = R.color.lcweb_red_1),
                    dayContentColor = colorResource(id = R.color.lcweb_gray_1),
                    todayContentColor = colorResource(id = R.color.lcweb_red_1),
                    currentYearContentColor = colorResource(id = R.color.lcweb_red_1),
                    dateTextFieldColors = TextFieldDefaults.colors(
                        cursorColor = colorResource(id = R.color.lcweb_red_1),
                        focusedLabelColor = colorResource(id = R.color.lcweb_red_1),
                        unfocusedLabelColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                        errorTextColor = colorResource(id = R.color.lcweb_red_1),
                        errorLabelColor = colorResource(id = R.color.lcweb_red_1),
                        focusedIndicatorColor = colorResource(id = R.color.lcweb_red_1),
                        errorIndicatorColor = colorResource(id = R.color.lcweb_red_1),
                        unfocusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                        errorSupportingTextColor = colorResource(id = R.color.lcweb_red_1)
                    )


                )
            )
        }
    }

}