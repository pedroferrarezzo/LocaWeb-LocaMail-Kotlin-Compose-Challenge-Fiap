package br.com.fiap.llocalweb.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun LoginScreen(navController: NavController) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .width(280.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 25.dp),
            text = "Opa, você de volta? Seja bem-vindo(a)!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "Imagem de boas vindas"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally,


            ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .width(330.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0XFFFF0142)
                ),
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Email") },
                placeholder = {
                    Text("Insira seu email")
                },

                )


            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .width(330.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0XFFFF0142)
                ),
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Senha")
                },
                placeholder = {
                    Text("Insira sua senha")
                }
            )
            Text(
                text = "Esqueceu sua senha?", modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 35.dp), color = Color(0xFFFF0142)
            )
        }

        Button(
            onClick = { /*TODO*/ },
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

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp)
        ) {
            Text(text = "Não possui cadastro?")
            Text(
                text = "Registre-se", color = Color(0xFFFF0142),
                modifier = Modifier.clickable(enabled = true, onClick = {
                    navController.navigate("signup")
                })
            )
        }

    }
}