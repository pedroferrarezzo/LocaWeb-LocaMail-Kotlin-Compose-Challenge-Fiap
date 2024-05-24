package br.com.fiap.locawebmailapp.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.locawebmailapp.R

@Composable
fun ErrorComponent(
    title: String,
    subtitle: String,
    painter: Painter,
    descriptionimage: String,
    modifier: Modifier,
    modifierButton: Modifier,
    buttonChange: () -> Unit,
    textButton: String

) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 10.dp, vertical = 30.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 50.sp,
                color = colorResource(id = R.color.lcweb_gray_1),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                fontSize = 30.sp,
                color = colorResource(id = R.color.lcweb_gray_1),
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp
            )

            Image(
                painter = painter,
                contentDescription = descriptionimage,
                modifier = modifier
            )
        }

        Button(
            onClick = buttonChange,
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = colorResource(id = R.color.lcweb_red_1)
                ),
            modifier = modifierButton,
            elevation = ButtonDefaults.buttonElevation(6.dp)

        ) {
            Text(
                text = textButton,
                color = colorResource(id = R.color.white)
            )
        }
    }

}