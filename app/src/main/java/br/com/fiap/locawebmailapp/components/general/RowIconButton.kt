package br.com.fiap.locawebmailapp.components.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
@Composable
fun RowIconButton(
    horizontalArrangement: Arrangement.Horizontal,
    modifier: Modifier,
    onClickFirstButton: () -> Unit,
    onClickSecondButton: () -> Unit,
    iconFirstButton: ImageVector,
    textSecondButton: String,
    descriptionFirstButton: String,
    colorsSecondButton: ButtonColors,
    colorSecondTextButton: Color

) {
    Row(
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
    ) {
        IconButton(
            onClick = onClickFirstButton) {
            Icon(imageVector = iconFirstButton, contentDescription = descriptionFirstButton)
        }

        Button(
            onClick = onClickSecondButton,
            colors = colorsSecondButton
        ) {
            Text(
                text = textSecondButton,
                color = colorSecondTextButton
            )
        }
    }

}