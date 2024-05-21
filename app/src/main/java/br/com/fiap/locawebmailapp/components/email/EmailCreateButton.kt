package br.com.fiap.locawebmailapp.components.email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.fiap.locawebmailapp.R

@Composable
fun EmailCreateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,) {
    Column(
        modifier = modifier
    ) {
        Button(
            onClick = onClick,
            elevation = ButtonDefaults.buttonElevation(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lcweb_red_1)),
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .padding(vertical = 5.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = stringResource(id = R.string.content_desc_mail_box),
                tint = colorResource(id = R.color.white)
            )
        }
    }
}