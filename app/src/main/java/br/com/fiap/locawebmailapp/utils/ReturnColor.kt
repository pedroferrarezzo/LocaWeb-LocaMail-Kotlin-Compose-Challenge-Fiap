package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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