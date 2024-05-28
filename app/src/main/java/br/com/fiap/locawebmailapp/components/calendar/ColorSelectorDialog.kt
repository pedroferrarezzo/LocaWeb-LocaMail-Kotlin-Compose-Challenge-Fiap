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
fun ColorSelectorDalog(
    openDialogColorPicker: MutableState<Boolean>,
    selectedColor: MutableState<Int>
) {
    if (openDialogColorPicker.value) {
        Dialog(onDismissRequest = { openDialogColorPicker.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
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
                            selected = selectedColor.value == 1,
                            onClick = {
                                selectedColor.value = 1
                                openDialogColorPicker.value = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.lcweb_colortodo_1),
                                unselectedColor = colorResource(id = R.color.lcweb_colortodo_1)
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.calendar_color_yellow),
                            modifier = Modifier.padding(2.dp),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.lcweb_colortodo_1)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedColor.value == 2,
                            onClick = {
                                selectedColor.value = 2
                                openDialogColorPicker.value = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.lcweb_colortodo_2),
                                unselectedColor = colorResource(id = R.color.lcweb_colortodo_2)
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.calendar_color_Pink),
                            modifier = Modifier.padding(2.dp),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.lcweb_colortodo_2)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedColor.value == 3,
                            onClick = {
                                selectedColor.value = 3
                                openDialogColorPicker.value = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.lcweb_colortodo_3),
                                unselectedColor = colorResource(id = R.color.lcweb_colortodo_3)
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.calendar_color_blue),
                            modifier = Modifier.padding(2.dp),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.lcweb_colortodo_3)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedColor.value == 4,
                            onClick = {
                                selectedColor.value = 4
                                openDialogColorPicker.value = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.lcweb_colortodo_4),
                                unselectedColor = colorResource(id = R.color.lcweb_colortodo_4)
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.calendar_color_red),
                            modifier = Modifier.padding(2.dp),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.lcweb_colortodo_4)
                        )
                    }
                }
            }
        }
    }
}