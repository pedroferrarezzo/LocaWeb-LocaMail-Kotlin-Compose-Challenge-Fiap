package br.com.fiap.locawebmailapp.components.email

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.convertTo12Hours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailViewButton(
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit,
    isRead: MutableState<Boolean>,
    redLcWeb: Color,
    respostasEmail: List<RespostaEmail>,
    onClickPastaIconButton: () -> Unit = {},
    onClickPastaPastaPickerDialog: (pasta: Pasta) -> Unit = {},
    onClickImportantButton: () -> Unit = {},
    isImportant: MutableState<Boolean> = mutableStateOf(false),
    attachEmailList: List<Long>,
    listPastaState: SnapshotStateList<Pasta> = mutableStateListOf(),
    timeState: TimePickerState,
    openDialogPastaPicker: MutableState<Boolean> = mutableStateOf(false),
    email: EmailComAlteracao,
    moveIconOption: Boolean = false,
    importantIconOption: Boolean = true,
    onClickMoveButton: () -> Unit = {},
    usuarioSelecionado: MutableState<Usuario>

    ) {
    Button(
        onClick = onClickButton,
        modifier =

        if (isRead.value) {
            Modifier
                .fillMaxWidth()
        } else {
            Modifier
                .fillMaxWidth()
                .drawBehind {
                    val borderSize = 2.dp.toPx()
                    val y = size.height - borderSize / 2

                    drawLine(
                        redLcWeb,
                        Offset(0f, y),
                        Offset(size.width, y),
                        borderSize
                    )
                }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.lcweb_gray_1)
        ),
        shape = RectangleShape,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (respostasEmail.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.reply_solid),
                            contentDescription = stringResource(id = R.string.content_desc_lcweb_reply),
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .padding(horizontal = 5.dp)
                        )
                    }

                    if (email.email.agenda_atrelada) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = stringResource(id = R.string.content_desc_event),
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp)
                                .padding(horizontal = 5.dp)
                        )
                    }

                    if (email.email.id_usuario == usuarioSelecionado.value.id_usuario) {
                        Text(
                            text = if (email.email.destinatario.length > 25) {
                                "${stringResource(id = R.string.mail_generic_to)} ${
                                    email.email.destinatario.take(
                                        25
                                    )
                                }..."
                            } else {
                                "${stringResource(id = R.string.mail_generic_to)} ${email.email.destinatario}"
                            },
                            maxLines = 1
                        )

                    }
                    else {
                        Text(
                            text = if (email.email.remetente.length > 25) {
                                "${stringResource(id = R.string.mail_generic_from)} ${
                                    email.email.remetente.take(
                                        25
                                    )
                                }..."
                            } else {
                                "${stringResource(id = R.string.mail_generic_from)} ${email.email.remetente}"
                            },
                            maxLines = 1
                        )

                    }


                }


                Text(text = email.email.assunto)
                Text(
                    text = if (email.email.corpo.length > 25) {
                        "${email.email.corpo.take(25)}..."
                    } else {
                        email.email.corpo
                    },
                    maxLines = 1
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    if (attachEmailList.contains(email.email.id_email)) {
                        Icon(
                            painter = painterResource(id = R.drawable.paperclip_solid),
                            contentDescription = stringResource(id = R.string.content_desc_clips),
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .padding(horizontal = 5.dp)
                        )
                    }

                    Text(
                        text = if (timeState.is24hour) email.email.horario else convertTo12Hours(
                            email.email.horario
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (moveIconOption) {
                        IconButton(onClick = onClickMoveButton) {
                            Icon(
                                painter = painterResource(id = R.drawable.turn_up_solid),
                                contentDescription = stringResource(id = R.string.content_desc_up),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        }
                    }

                    if (listPastaState.isNotEmpty()) {
                        IconButton(onClick = onClickPastaIconButton) {
                            Icon(
                                painter = painterResource(id = R.drawable.box_archive_solid),
                                contentDescription = stringResource(id = R.string.content_desc_box_archived),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        }

                        PastaPickerDalog(
                            openDialogPastaPicker = openDialogPastaPicker,
                            listPasta = listPastaState,
                            color = redLcWeb,
                            onClickPasta = onClickPastaPastaPickerDialog
                        )
                    }

                    if (importantIconOption) {
                        IconButton(onClick = onClickImportantButton) {
                            Icon(
                                imageVector = if (isImportant.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.content_desc_favorite),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}