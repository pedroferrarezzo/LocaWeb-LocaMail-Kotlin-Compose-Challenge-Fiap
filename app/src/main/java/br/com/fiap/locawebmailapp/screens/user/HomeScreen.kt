package br.com.fiap.llocalweb.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.generateSha256

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val usuarioRepository = UsuarioRepository(context)

    val emailExistente = usuarioRepository.retornaUsarioPorEmail("dev@locaweb.com.br")


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(top = 150.dp),
            painter = painterResource(id = R.drawable.locaweb),
            contentDescription = stringResource(id = R.string.content_desc_lcweb_logo)
        )
        Column {
            Text(
                text = stringResource(id = R.string.register_welcome_title),
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.lcweb_gray_1)
            )

            Button(
                onClick = {
                    if (emailExistente == null) {
                        val senha = generateSha256("@quweuqweusudausdu@123323Sdsdiadi1j23asd123S\$\$\$%232@#1skls")
                        usuarioRepository.criarUsuario(
                            Usuario(
                                nome = "Dev",
                                email = "dev@locaweb.com.br",
                                senha = senha,
                                autenticado = false,
                                selected_user = false
                            )
                        )
                    }
                    navController.navigate("loginscreen")
                          },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .width(330.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.lcweb_red_1)),
                shape = RoundedCornerShape(15.dp)
            ) {


                Text(
                    text = stringResource(
                        id = R.string.register_screen_advance),
                    color = colorResource(id = R.color.white))
            }
        }
    }
}