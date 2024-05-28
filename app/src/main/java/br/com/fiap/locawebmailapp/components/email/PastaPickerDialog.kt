package br.com.fiap.locawebmailapp.components.email

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.Pasta

@Composable
fun PastaPickerDalog(
    openDialogPastaPicker: MutableState<Boolean>,
    listPasta: SnapshotStateList<Pasta>,
    onClickPasta: (pasta: Pasta) -> Unit,
    color: Color
) {
    if (openDialogPastaPicker.value) {
        Dialog(onDismissRequest = { openDialogPastaPicker.value = false }) {
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Column(
                    ) {
                        Text(
                            text = stringResource(id = R.string.mail_pasta_picker_move),
                            color = colorResource(id = R.color.lcweb_gray_1),
                            fontSize = 25.sp,
                            modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.Start)
                        )

                        LazyColumn(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            items(listPasta) {
                                Button(
                                    onClick = {onClickPasta(it)},
                                    modifier = Modifier
                                            .fillMaxWidth()
                                            .drawBehind {
                                                val borderSize = 2.dp.toPx()
                                                val y = size.height - borderSize / 2

                                                drawLine(
                                                        color,
                                                        Offset(0f, y),
                                                        Offset(size.width, y),
                                                        borderSize
                                                )
                                            },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RectangleShape
                                ) {

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.box_archive_solid),
                                            contentDescription = stringResource(id = R.string.content_desc_box_archived),
                                            tint = colorResource(
                                                id = R.color.lcweb_gray_1
                                            ),
                                            modifier = Modifier
                                                    .width(15.dp)
                                                    .height(15.dp)
                                        )
                                        Text(
                                            text = it.nome,
                                            color = colorResource(id = R.color.lcweb_gray_1),
                                            fontSize = 15.sp,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}