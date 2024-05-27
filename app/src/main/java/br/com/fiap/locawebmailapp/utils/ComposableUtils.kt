package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import br.com.fiap.locawebmailapp.R

@Composable
fun returnColor(option: Int): Color {
    var color: Color = Color.Transparent;
    when (option) {
        1 -> color = colorResource(id = R.color.lcweb_colortodo_1)
        2 -> color = colorResource(id = R.color.lcweb_colortodo_2)
        3 -> color = colorResource(id = R.color.lcweb_colortodo_3)
        4 -> color = colorResource(id = R.color.lcweb_colortodo_4)
    }

    return color
}


@Composable
fun returnStringRepeatOption(option: Int): String {
    var stringOption = ""
    when (option) {
        1 -> stringOption = stringResource(id = R.string.calendar_options_not_repeat)
        2 -> stringOption = stringResource(id = R.string.calendar_options_every_day_repeat)
    }

    return stringOption
}

