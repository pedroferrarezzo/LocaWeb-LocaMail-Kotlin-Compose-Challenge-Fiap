package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.locawebmailapp.R

@Composable
fun RepeatSelectorDialog(
    openDialogRepeatPicker: MutableState<Boolean>,
    selectedRepeat: MutableState<Int>
) {
    if (openDialogRepeatPicker.value) {
        Dialog(onDismissRequest = { openDialogRepeatPicker.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        colorResource(id = R.color.lcweb_gray_5)
                    } else {
                        colorResource(id = R.color.white)
                    }
                ),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedRepeat.value == 1,
                            onClick = {
                                selectedRepeat.value = 1
                                openDialogRepeatPicker.value = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.calendar_repeat_selector_no),
                            modifier = Modifier.padding(2.dp),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedRepeat.value == 2,
                            onClick = {
                                selectedRepeat.value = 2
                                openDialogRepeatPicker.value = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.lcweb_gray_1),
                                unselectedColor = colorResource(id = R.color.lcweb_gray_1)
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.calendar_repeat_selector_all_day),
                            modifier = Modifier.padding(2.dp),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }
                }
            }
        }
    }
}