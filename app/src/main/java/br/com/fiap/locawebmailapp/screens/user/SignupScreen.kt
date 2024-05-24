package br.com.fiap.llocalweb.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun SignupScreen(navController: NavController) {

    var nome by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 25.dp),
            text = "CADASTRO",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold

        )

        Image(
            painter = painterResource(id = R.drawable.cadastro),
            contentDescription = "Icone cadastro",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(330.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0XFFFF0142)
                ),
                value = nome,
                onValueChange = {
                    nome = it
                },
                label = { Text("Nome completo") },
                placeholder = {
                    Text("Insira seu nome completo")
                },

                )
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
                    .padding(bottom = 30.dp)
                    .width(330.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0XFFFF0142)
                ),
                value = senha,
                onValueChange = {
                    senha = it
                },
                label = { Text("Senha") },
                placeholder = {
                    Text("Insira sua senha")
                },
            )

            Row(
                modifier = Modifier.width(330.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .width(200.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0XFFFF0142)
                    ),
                    label = { Text("Imagem do perfil") },
                    value = "",
                    onValueChange = { },
                )
                Button(
                    onClick = { /* ... */ },
                    modifier = Modifier
                        .height(56.dp)
                        .width(150.dp)
                        .padding(top = 12.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF0142)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        "Abrir galeria",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
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


                Text(
                    text = "Entrar",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Login",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 30.dp, top = 10.dp)
                    .clickable { navController.navigate("login") },
                color = Color(0xFFFF0142),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

        }


    }

}