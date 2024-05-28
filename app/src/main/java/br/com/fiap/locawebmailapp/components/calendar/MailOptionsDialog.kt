package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.locawebmailapp.R

@Composable
fun MailOptionsDialog(
    openDialogMailOptions: MutableState<Boolean>,
    selectedMailOption: MutableState<Int>
) {
    if (openDialogMailOptions.value) {
        Dialog(onDismissRequest = { openDialogMailOptions.value = false }) {
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

                    Button(
                        onClick = {
                            selectedMailOption.value = 1
                            openDialogMailOptions.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ban_solid),
                                contentDescription = stringResource(
                                    id = R.string.content_desc_nothing_do
                                ),
                                tint = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.calendar_mail_options_selector_no),
                                modifier = Modifier.padding(5.dp),
                                fontSize = 20.sp,
                                color = colorResource(id = R.color.lcweb_gray_1)
                            )

                            if (selectedMailOption.value == 1) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = stringResource(
                                        id = R.string.content_desc_check
                                    ),
                                    tint = colorResource(
                                        id = R.color.lcweb_gray_1
                                    )
                                )
                            }
                        }

                    }

                    Button(
                        onClick = {
                            selectedMailOption.value = 2
                            openDialogMailOptions.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = stringResource(id = R.string.content_desc_mail_send),
                                tint = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.calendar_mail_options_selector_invite),
                                modifier = Modifier.padding(5.dp),
                                fontSize = 20.sp,
                                color = colorResource(id = R.color.lcweb_gray_1)
                            )
                            if (selectedMailOption.value == 2) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = stringResource(
                                        id = R.string.content_desc_check
                                    ),
                                    tint = colorResource(
                                        id = R.color.lcweb_gray_1
                                    )
                                )
                            }
                        }

                    }

                    Button(
                        onClick = {
                            selectedMailOption.value = 3
                            openDialogMailOptions.value = false

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pen_to_square_solid),
                                contentDescription = stringResource(
                                    id = R.string.content_desc_drafts
                                ),
                                tint = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.calendar_mail_options_selector_draft),
                                modifier = Modifier.padding(5.dp),
                                fontSize = 20.sp,
                                color = colorResource(id = R.color.lcweb_gray_1)
                            )
                            if (selectedMailOption.value == 3) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = stringResource(
                                        id = R.string.content_desc_check
                                    ),
                                    tint = colorResource(
                                        id = R.color.lcweb_gray_1
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}