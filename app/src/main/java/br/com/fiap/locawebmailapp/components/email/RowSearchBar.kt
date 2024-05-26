package br.com.fiap.locawebmailapp.components.email

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.user.UserSelectorDalog
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun <T> RowSearchBar(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    scope: CoroutineScope,
    openDialogUserPicker: MutableState<Boolean>,
    textSearchBar: MutableState<String>,
    applyStateListUserSelectorDialog: () -> Unit = {},
    usuarioSelecionado: MutableState<Usuario>,
    stateEmailList: SnapshotStateList<T> = mutableStateListOf(),
    usuarioRepository: UsuarioRepository,
    placeholderTextFieldSearch: String,
    selectedDrawerPasta: MutableState<String>,
    navController: NavController

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = textSearchBar.value,
            onValueChange = {
                textSearchBar.value = it
            },
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = placeholderTextFieldSearch
                )
            },
            leadingIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = stringResource(
                        id = R.string.content_desc_menu
                    )
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        openDialogUserPicker.value = !openDialogUserPicker.value
                    }
                ) {
                    Image(
                        bitmap = byteArrayToBitmap(usuarioSelecionado.value.profile_image).asImageBitmap(),
                        contentDescription = stringResource(id = R.string.content_desc_iconregister),
                        modifier = Modifier
                            .size(30.dp)
                            .clip(shape = CircleShape)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.lcweb_red_1),
                unfocusedContainerColor = colorResource(id = R.color.lcweb_red_1),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLeadingIconColor = colorResource(id = R.color.white),
                unfocusedLeadingIconColor = colorResource(id = R.color.white),
                focusedPlaceholderColor = colorResource(id = R.color.white),
                unfocusedPlaceholderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white),
                focusedTextColor = colorResource(id = R.color.white),
                unfocusedTextColor = colorResource(id = R.color.white),
                focusedSupportingTextColor = Color.Transparent,
                unfocusedSupportingTextColor = Color.Transparent,
                focusedTrailingIconColor = colorResource(id = R.color.white),
                unfocusedTrailingIconColor = colorResource(id = R.color.white)
            ),
            textStyle = TextStyle(
                textDecoration = TextDecoration.None
            ),
            singleLine = true,
            shape = RoundedCornerShape(5.dp)
        )
    }

    UserSelectorDalog(
        openDialogUserPicker = openDialogUserPicker,
        usuarioSelecionado = usuarioSelecionado,
        stateList = stateEmailList,
        applyStateList = applyStateListUserSelectorDialog,
        usuarioRepository = usuarioRepository,
        selectedDrawerPasta = selectedDrawerPasta,
        navController = navController
    )
}