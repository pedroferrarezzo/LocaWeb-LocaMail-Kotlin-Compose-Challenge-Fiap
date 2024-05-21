package br.com.fiap.locawebmailapp.components.email

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.locawebmailapp.R

@Composable
fun RowCrudMail(
    onClickBack: () -> Unit,
    onClickPaperClip: () -> Unit,
    onClickSend: () -> Unit,
    bitmapList: SnapshotStateList<Bitmap>,
    isDraft: Boolean = false,
    onClickDeleteDraft: () -> Unit = {},
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

            if (isDraft) {
                IconButton(onClick = onClickDeleteDraft) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.content_desc_trash),
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }
            }

            IconButton(onClick = onClickPaperClip) {
                Icon(
                    painter = painterResource(id = R.drawable.paperclip_solid),
                    contentDescription = stringResource(id = R.string.content_desc_clips),
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                    tint = colorResource(id = R.color.lcweb_gray_1)
                )
            }

            IconButton(onClick = onClickSend) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = stringResource(id = R.string.content_desc_mail_send),
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp),
                    tint = colorResource(id = R.color.lcweb_gray_1)
                )
            }
        }
    }

    LazyRow {
        items(bitmapList) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = stringResource(id = R.string.content_desc_img_selected),
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                )
                IconButton(
                    onClick = {
                        bitmapList.remove(it)
                    },
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(id = R.string.content_desc_clear),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }
            }
        }
    }
}