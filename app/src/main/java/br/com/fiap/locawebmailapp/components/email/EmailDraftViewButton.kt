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
import androidx.compose.material.icons.filled.Edit
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
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.utils.convertTo12Hours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDraftViewButton(
    onClickButton: () -> Unit,
    attachEmailList: List<Long>,
    timeState: TimePickerState,
    email: Email,
    ) {
    Button(
        onClick = onClickButton,
        modifier = Modifier
            .fillMaxWidth(),
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

                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.content_desc_edit),
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .padding(horizontal = 5.dp),
                        tint = colorResource(id = R.color.lcweb_red_1)
                    )


                    Text(
                        text = if (email.destinatario.length > 25) {
                            "${stringResource(id = R.string.mail_generic_to)} ${
                                email.destinatario.take(
                                    25
                                )
                            }..."
                        } else {
                            "${stringResource(id = R.string.mail_generic_to)} ${email.destinatario}"
                        },
                        maxLines = 1
                    )
                }
                Text(text = email.assunto)
                Text(
                    text = if (email.corpo.length > 25) {
                        "${email.corpo.take(25)}..."
                    } else {
                        email.corpo
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


                    if (attachEmailList.contains(email.id_email)) {
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
                        text = if (timeState.is24hour) email.horario else convertTo12Hours(
                            email.horario
                        )
                    )
                }
            }
        }
    }
}