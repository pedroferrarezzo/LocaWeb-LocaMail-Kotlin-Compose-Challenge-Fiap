package br.com.fiap.locawebmailapp.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R

@Composable
fun ExpandedShadowDropdown(
    navController: NavController,
    expanded: MutableState<Boolean>
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            modifier = Modifier.background(colorResource(id = R.color.lcweb_gray_1)),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.calendar_dropdown_task), color = colorResource(id = R.color.white)) },
                onClick = {
                    expanded.value = false
                    navController.navigate("criatarefascreen")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = stringResource(id = R.string.content_desc_task), tint = colorResource(id = R.color.white)
                    )
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.calendar_dropdown_event), color = colorResource(id = R.color.white)) },
                onClick = {
                    expanded.value = false
                    navController.navigate("criaeventoscreen")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = stringResource(id = R.string.content_desc_event), tint = colorResource(id = R.color.white)
                    )
                }
            )
        }
    }
}