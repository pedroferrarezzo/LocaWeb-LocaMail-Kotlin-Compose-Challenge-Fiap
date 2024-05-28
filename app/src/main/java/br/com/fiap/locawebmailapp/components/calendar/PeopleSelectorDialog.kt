package br.com.fiap.locawebmailapp.components.calendar

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Usuario

@Composable
fun PeopleSelectorDalog(
    openDialogPeoplePicker: MutableState<Boolean>,
    listConvidado: MutableState<List<Convidado>>,
    listConvidadoSelected: MutableList<Convidado>,
    listConvidadoText: MutableState<String>,
    usuarioSelecionado: MutableState<Usuario>
) {
    if (openDialogPeoplePicker.value) {
        Dialog(onDismissRequest = { openDialogPeoplePicker.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp),
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

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextFieldCalendar(
                        value = listConvidadoText.value,
                        onValueChange = {
                            listConvidadoText.value = it
                        },
                        placeholderText = stringResource(id = R.string.calendar_event_people_selector_placeholder),
                        fontSizePlaceHolder = 20.sp,
                        fontSizeTextStyle = 20.sp,
                        paddingTextField = 20.dp,
                        descriptionTrailingIcon = "",
                        iconTrailingIcon = Icons.Outlined.Search,
                        isReadOnly = false,
                        maxLines = 1,
                        isError = false
                    )

                    LazyColumn(
                        reverseLayout = false,
                        content = {
                            items(
                                listConvidado.value.filter {
                                    it.email != usuarioSelecionado.value.email
                                }.reversed(),
                                key = {
                                    it.id_convidado
                                }
                            ) {
                                if (it.email.contains(listConvidadoText.value)) {
                                    Button(
                                        onClick = {
                                            if (!listConvidadoSelected.contains(it)) {
                                                listConvidadoSelected.add(it)
                                                listConvidadoText.value = ""
                                            } else {
                                                listConvidadoSelected.remove(it)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RectangleShape


                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = it.email,
                                                color = colorResource(id = R.color.lcweb_gray_1),
                                                fontSize = 20.sp
                                            )

                                            if (listConvidadoSelected.contains(it)) {
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
                    )
                }
            }
        }
    }
}