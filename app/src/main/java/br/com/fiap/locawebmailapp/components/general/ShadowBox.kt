package br.com.fiap.locawebmailapp.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import br.com.fiap.locawebmailapp.R

@Composable
fun ShadowBox(expanded: MutableState<Boolean>) {
    if(expanded.value) {
        Box(
            modifier = Modifier
                .background(colorResource(id = R.color.lcweb_black_4))
                .fillMaxSize()
        )
    }
}