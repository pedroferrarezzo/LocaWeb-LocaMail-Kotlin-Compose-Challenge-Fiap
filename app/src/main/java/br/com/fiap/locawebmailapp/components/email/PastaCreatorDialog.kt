package br.com.fiap.locawebmailapp.components.email

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.PastaRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Pasta

@Composable
fun PastaCreatorDalog(
    openDialogPastaCreator: MutableState<Boolean>,
    textPastaCreator: MutableState<String>,
    usuarioRepository: UsuarioRepository,
    pastaRepository: PastaRepository,
    listPastaState: SnapshotStateList<Pasta>
) {
    if (openDialogPastaCreator.value) {
        Dialog(onDismissRequest = { openDialogPastaCreator.value = false }) {
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

                    TextField(
                        value = textPastaCreator.value,
                        onValueChange = {
                            textPastaCreator.value = it
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.box_archive_solid),
                                modifier = Modifier
                                    .width(15.dp)
                                    .height(15.dp),
                                contentDescription = stringResource(id = R.string.content_desc_box_archived)
                            )
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.mail_pasta_creator_folder_name))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .align(Alignment.Center),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            cursorColor = colorResource(id = R.color.lcweb_red_1),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                            focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                            focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                            focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                            errorTextColor = colorResource(id = R.color.lcweb_red_1),
                            errorContainerColor = Color.Transparent,
                            errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                            errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                            errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                            errorIndicatorColor = colorResource(id = R.color.lcweb_red_1),
                            focusedLeadingIconColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedLeadingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            textDecoration = TextDecoration.None
                        )
                    )

                    Row(
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        TextButton(onClick = { openDialogPastaCreator.value = false }) {
                            Text(text = stringResource(id = R.string.mail_pasta_creator_cancel), color = colorResource(id = R.color.lcweb_red_1))

                        }

                        TextButton(onClick = {
                            val rowId = pastaRepository.criarPasta(
                                Pasta(
                                    nome = textPastaCreator.value,
                                    id_usuario = usuarioRepository.listarUsuarioSelecionado().id_usuario
                                )
                            )

                            listPastaState.add(Pasta(
                                id_pasta = rowId,
                                nome = textPastaCreator.value,
                                id_usuario = usuarioRepository.listarUsuarioSelecionado().id_usuario
                            ))
                            openDialogPastaCreator.value = false
                            textPastaCreator.value = ""
                        }) {
                            Text(text = stringResource(id = R.string.mail_pasta_creator_create), color = colorResource(id = R.color.lcweb_red_1))
                        }
                    }
                }
            }
        }
    }
}