package br.com.fiap.locawebmailapp.components.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Usuario

@Composable
fun <T> UserSelectorDalog(
    openDialogUserPicker: MutableState<Boolean>,
    usuarioSelecionado: MutableState<Usuario>,
    stateList: SnapshotStateList<T> = mutableStateListOf(),
    applyStateList: () -> Unit = {},
    usuarioRepository: UsuarioRepository,
    navController: NavController,
    selectedDrawerPasta: MutableState<String>

    ) {
    if (openDialogUserPicker.value) {
        Dialog(onDismissRequest = { openDialogUserPicker.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                ),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(5.dp)
                    ) {

                        Text(
                            text = "IMAGEM AQUI",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Column {
                            Text(text = usuarioSelecionado.value.nome)
                            Text(text = usuarioSelecionado.value.email)
                        }
                    }

                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )


                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(usuarioRepository.listarUsuariosNaoSelecionados()) {
                            Button(
                                onClick = {
                                    usuarioRepository.desselecionarUsuarioSelecionadoAtual()
                                    usuarioRepository.selecionarUsuario(it.id_usuario)
                                    usuarioSelecionado.value = it

                                    stateList.clear()
                                    applyStateList()

                                    if (selectedDrawerPasta.value != "") {
                                        navController.popBackStack()
                                    }

                                    openDialogUserPicker.value = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = colorResource(id = R.color.lcweb_gray_1)
                                ),
                                shape = RectangleShape,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(5.dp)
                                ) {

                                    Text(
                                        text = "IMAGEM AQUI",
                                        modifier = Modifier.padding(end = 5.dp)
                                    )

                                    Column {
                                        Text(text = it.nome)
                                        Text(text = it.email)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}