package br.com.fiap.locawebmailapp.components.email

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.AnexoRespostaEmailRepository
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import br.com.fiap.locawebmailapp.utils.dateToCompleteStringDate
import br.com.fiap.locawebmailapp.utils.stringParaLista
import br.com.fiap.locawebmailapp.utils.stringToLocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnEmailDetails(
    modifier: Modifier = Modifier,
    onClickDraftRespostaEmailDelete: (respostaEmail: RespostaEmail) -> Unit,
    onClickDraftRespostaEmailEdit: (respostaEmail: RespostaEmail) -> Unit,
    email: Email,
    anexoBitMapList: List<Bitmap>,
    timeState: TimePickerState,
    usuarioSelecionado: MutableState<Usuario>,
    anexoRespostaEmailRepository: AnexoRespostaEmailRepository,
    respostasEmailStateList: SnapshotStateList<RespostaEmail>,
    isTodasContasScreen: Boolean,
    onClickReply: (respostaEmail: RespostaEmail) -> Unit,
    isAgendaAtrelada: MutableState<Boolean>,
    onClickRejectInviteButton: () -> Unit,
    onClickAcceptInviteButton: () -> Unit,
    agendaEmailStateList: SnapshotStateList<Agenda>
) {
    LazyColumn {

        if (isAgendaAtrelada.value) {
            item {
                if (email.assunto.isNotBlank()) {
                    Text(
                        text = email.assunto,
                        fontSize = 30.sp,
                        lineHeight = 30.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)
                    )
                }
                HorizontalDivider(
                    color = colorResource(id = R.color.lcweb_red_1)
                )

                Row {
                    Text(
                        text = stringResource(id = R.string.mail_generic_from),
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = email.remetente,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)

                    )
                }

                HorizontalDivider(
                    color = colorResource(id = R.color.lcweb_red_1)
                )

                Row {
                    Text(
                        text = stringResource(id = R.string.mail_generic_to),
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = email.destinatario,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)

                    )
                }
                HorizontalDivider(
                    color = colorResource(id = R.color.lcweb_red_1)
                )

                if (usuarioSelecionado.value.email != email.remetente && !isTodasContasScreen && agendaEmailStateList.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onClickRejectInviteButton,
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.lcweb_red_1)
                            ),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            Text(text = stringResource(id = R.string.mail_event_reject))
                        }


                        Button(
                            onClick = onClickAcceptInviteButton,
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.lcweb_red_1)
                            ),
                            contentPadding = PaddingValues(5.dp),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Text(text = stringResource(id = R.string.mail_event_accept))
                        }

                    }
                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                }

                if (email.corpo.isNotBlank()) {
                    Text(
                        text = email.corpo,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Justify
                    )

                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                }

            }

        } else {
            item {
                if (email.assunto.isNotBlank()) {
                    Text(
                        text = email.assunto,
                        fontSize = 30.sp,
                        lineHeight = 30.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)
                    )
                }

                LazyRow(modifier = Modifier.padding(bottom = 5.dp)) {
                    items(anexoBitMapList) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                        ) {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = stringResource(id = R.string.content_desc_img_selected),
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(70.dp)
                            )
                        }
                    }
                }


                HorizontalDivider(
                    color = colorResource(id = R.color.lcweb_red_1)
                )

                Row {
                    Text(
                        text = stringResource(id = R.string.mail_generic_from),
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = email.remetente,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)

                    )
                }

                HorizontalDivider(
                    color = colorResource(id = R.color.lcweb_red_1)
                )

                Row {
                    Text(
                        text = stringResource(id = R.string.mail_generic_to),
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = email.destinatario,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)

                    )
                }

                HorizontalDivider(
                    color = colorResource(id = R.color.lcweb_red_1)
                )

                if (email.cc.isNotBlank()) {
                    Row {
                        Text(
                            text = stringResource(id = R.string.mail_generic_cc),
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_red_1),
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = email.cc,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.lcweb_gray_1),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                }

                Row {
                    Text(
                        text = stringResource(id = R.string.mail_generic_date),
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = dateToCompleteStringDate(stringToLocalDate(email.data)) + " " + if (timeState.is24hour) email.horario else convertTo12Hours(
                            email.horario
                        ),
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp)
                    )
                }
                HorizontalDivider(
                    color = colorResource(id = R.color.lcweb_red_1)
                )

                if (email.corpo.isNotBlank()) {
                    Text(
                        text = email.corpo,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Justify
                    )

                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                )
            }

            items(respostasEmailStateList) { respostaEmail ->

                val anexoRespostaEmailArrayByteList =
                    anexoRespostaEmailRepository.listarAnexosArrayBytePorIdRespostaEmail(
                        respostaEmail.id_resposta_email
                    )
                val anexoRespostaEmailBitMapList = anexoRespostaEmailArrayByteList.map {
                    byteArrayToBitmap(it)
                }


                if (
                    stringParaLista(respostaEmail.destinatario).contains(usuarioSelecionado.value.email) ||
                    stringParaLista(respostaEmail.remetente).contains(usuarioSelecionado.value.email) ||
                    stringParaLista(respostaEmail.cc).contains(usuarioSelecionado.value.email) ||
                    stringParaLista(respostaEmail.cco).contains(usuarioSelecionado.value.email)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )

                    if (respostaEmail.editavel && usuarioSelecionado.value.id_usuario == respostaEmail.id_usuario) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = { onClickDraftRespostaEmailDelete(respostaEmail) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = colorResource(id = R.color.lcweb_gray_1)
                                ),
                                shape = RectangleShape
                            ) {
                                Text(text = stringResource(id = R.string.mail_generic_delete))
                            }


                            Button(
                                onClick = { onClickDraftRespostaEmailEdit(respostaEmail) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = colorResource(id = R.color.lcweb_gray_1)
                                ),
                                shape = RectangleShape
                            ) {
                                Text(text = stringResource(id = R.string.mail_generic_edit))
                            }

                        }

                        if (respostaEmail.assunto.isNotBlank()) {
                            Text(
                                text = respostaEmail.assunto,
                                fontSize = 30.sp,
                                lineHeight = 30.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)
                            )
                        }

                        LazyRow(modifier = Modifier.padding(bottom = 5.dp)) {
                            items(anexoRespostaEmailBitMapList) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(
                                        horizontal = 5.dp,
                                        vertical = 5.dp
                                    )
                                ) {
                                    Image(
                                        bitmap = it.asImageBitmap(),
                                        contentDescription = stringResource(id = R.string.content_desc_img_selected),
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(70.dp)
                                    )
                                }
                            }
                        }


                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )

                        Row {
                            Text(
                                text = stringResource(id = R.string.mail_generic_from),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = respostaEmail.remetente,
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)

                            )
                        }

                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )

                        Row {
                            Text(
                                text = stringResource(id = R.string.mail_generic_to),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = respostaEmail.destinatario,
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)

                            )
                        }

                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )

                        if (respostaEmail.cc.isNotBlank()) {
                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_cc),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = respostaEmail.cc,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )
                        }

                        Row {
                            Text(
                                text = stringResource(id = R.string.mail_generic_date),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = dateToCompleteStringDate(stringToLocalDate(email.data)) + " " + if (timeState.is24hour) email.horario else convertTo12Hours(
                                    email.horario
                                ),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )


                        if (respostaEmail.corpo.isNotBlank()) {
                            Text(
                                text = respostaEmail.corpo,
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp),
                                textAlign = TextAlign.Justify
                            )
                        }

                    } else if (!respostaEmail.editavel) {

                        Row(
                            modifier = Modifier.padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (respostaEmail.assunto.isNotBlank()) {
                                Text(
                                    text = respostaEmail.assunto,
                                    fontSize = 30.sp,
                                    lineHeight = 30.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1)
                                )
                            }

                            if (!isTodasContasScreen) {
                                IconButton(onClick = { onClickReply(respostaEmail) }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.reply_solid),
                                        contentDescription = stringResource(id = R.string.content_desc_lcweb_reply),
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp),
                                        tint = colorResource(id = R.color.lcweb_gray_1)
                                    )
                                }
                            }
                        }

                        LazyRow(modifier = Modifier.padding(bottom = 5.dp)) {
                            items(anexoRespostaEmailBitMapList) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(
                                        horizontal = 5.dp,
                                        vertical = 5.dp
                                    )
                                ) {
                                    Image(
                                        bitmap = it.asImageBitmap(),
                                        contentDescription = stringResource(id = R.string.content_desc_img_selected),
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(70.dp)
                                    )
                                }
                            }
                        }


                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )

                        Row {
                            Text(
                                text = stringResource(id = R.string.mail_generic_from),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = respostaEmail.remetente,
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)

                            )
                        }

                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )

                        Row {
                            Text(
                                text = stringResource(id = R.string.mail_generic_to),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = respostaEmail.destinatario,
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)

                            )
                        }

                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )

                        if (respostaEmail.cc.isNotBlank()) {
                            Row {
                                Text(
                                    text = stringResource(id = R.string.mail_generic_cc),
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_red_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = respostaEmail.cc,
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )
                        }

                        Row {
                            Text(
                                text = stringResource(id = R.string.mail_generic_date),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = dateToCompleteStringDate(stringToLocalDate(email.data)) + " " + if (timeState.is24hour) email.horario else convertTo12Hours(
                                    email.horario
                                ),
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        HorizontalDivider(
                            color = colorResource(id = R.color.lcweb_red_1)
                        )

                        if (respostaEmail.corpo.isNotBlank()) {
                            Text(
                                text = respostaEmail.corpo,
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.lcweb_gray_1),
                                modifier = Modifier.padding(5.dp),
                                textAlign = TextAlign.Justify
                            )

                            HorizontalDivider(
                                color = colorResource(id = R.color.lcweb_red_1)
                            )
                        }
                    }
                }
            }

        }


    }
}