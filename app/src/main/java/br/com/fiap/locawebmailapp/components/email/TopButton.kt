package br.com.fiap.locawebmailapp.components.email

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.EmailComAlteracao

@Composable
fun TopButton(
    modifier: Modifier = Modifier,
    textButton: String,
    onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.lcweb_gray_1)
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
    ) {
        Text(text = textButton)
    }
    HorizontalDivider(
        color = colorResource(id = R.color.lcweb_red_1)
    )

}