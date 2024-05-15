package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import br.com.fiap.locawebmailapp.R

@Stable
@Composable
fun TextFieldCalendar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    fontSizePlaceHolder: TextUnit,
    fontSizeTextStyle: TextUnit,
    paddingTextField: Dp,
    descriptionTrailingIcon: String,
    iconTrailingIcon: ImageVector,
    isReadOnly: Boolean,
    maxLines: Int,
    isError: Boolean
) {
    TextField(
        readOnly = isReadOnly,
        value = value,
        modifier = Modifier
            .padding(paddingTextField)
            .fillMaxWidth(),
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = fontSizePlaceHolder
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            cursorColor = colorResource(id = R.color.lcweb_red_1),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
            unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
            focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
            focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
            unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_2),
            focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
            unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
            errorTextColor = colorResource(id = R.color.lcweb_red_1),
            errorContainerColor = Color.Transparent,
            errorCursorColor = colorResource(id = R.color.lcweb_red_1),
            errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
            errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
            errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)

        ),
        textStyle = TextStyle(
            textDecoration = TextDecoration.None,
            fontSize = fontSizeTextStyle
        ),
        trailingIcon = {
            Icon(imageVector = iconTrailingIcon, contentDescription = descriptionTrailingIcon)
        },
        maxLines = maxLines,
        isError = isError
    )
}