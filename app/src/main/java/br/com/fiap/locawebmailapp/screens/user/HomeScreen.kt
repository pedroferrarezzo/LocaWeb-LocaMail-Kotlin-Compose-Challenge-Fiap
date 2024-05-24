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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R

@Composable
fun HomeScreen(navController: NavController) {
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
            contentDescription = "logo-locaweb"
        )
        Column {
            Text(
                text = "Conecte-se, organize-se e realize tarefas com email pessoal e calendário gratuitos.",
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { navController.navigate("loginscreen") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .width(330.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFF0142)),
                shape = RoundedCornerShape(15.dp)
            ) {


                Text(text = "Entrar")
            }

            OutlinedButton(
                onClick = {
                    navController.navigate("signupscreen")
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp, bottom = 30.dp)
                    .width(330.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, Color(0xFFFF0142)),


                ) {
                Text(text = "Cadastrar", color = Color(0xFFFF0142))
            }

        }
    }
}