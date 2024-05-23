package br.com.fiap.locawebmailapp.components.email

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.locawebmailapp.R

@Composable
fun RowTopOptionsViewEmail(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickDelete: () -> Unit,
    isExcluido: MutableState<Boolean>,
    isTodasContasScreen: Boolean,
    onClickReply: () -> Unit,
    onClickSpam: () -> Unit,
    onClickFavorite: () -> Unit,
    onClickArchive: () -> Unit,
    isSpam: MutableState<Boolean>,
    isImportant: MutableState<Boolean>,
    isArchive: MutableState<Boolean>,
    isAgendaAtrelada: MutableState<Boolean>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onClickBack

        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(id = R.string.content_desc_key_left),
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp),
                tint = colorResource(id = R.color.lcweb_gray_1)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.locaweb),
            contentDescription = stringResource(id = R.string.content_desc_lcweb_logo),
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)

        )


        Row {
            IconButton(onClick = onClickDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.content_desc_trash),
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                    tint = colorResource(id = R.color.lcweb_gray_1)
                )
            }

            if (!isExcluido.value) {

                if (!isTodasContasScreen && !isAgendaAtrelada.value) {
                    IconButton(onClick = onClickReply) {
                        Icon(
                            painter = painterResource(id = R.drawable.reply_solid),
                            contentDescription = stringResource(id = R.string.content_desc_lcweb_reply),
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }
                }

                IconButton(onClick = onClickSpam) {
                    Icon(
                        painter = if (isSpam.value) painterResource(id = R.drawable.exclamation_mark_filled) else painterResource(
                            id = R.drawable.exclamation_mark_outlined
                        ),
                        contentDescription = stringResource(id = R.string.content_desc_spam),
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        tint = colorResource(id = R.color.lcweb_red_1)
                    )
                }

                IconButton(onClick = onClickArchive) {
                    Icon(
                        painter = if (isArchive.value) painterResource(id = R.drawable.folder_open_solid) else painterResource(
                            id = R.drawable.folder_open_regular
                        ),
                        contentDescription = stringResource(id = R.string.content_desc_folder),
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }

                IconButton(onClick = onClickFavorite) {
                    Icon(
                        imageVector = if (isImportant.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.content_desc_favorite),
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }
            }
        }
    }
}