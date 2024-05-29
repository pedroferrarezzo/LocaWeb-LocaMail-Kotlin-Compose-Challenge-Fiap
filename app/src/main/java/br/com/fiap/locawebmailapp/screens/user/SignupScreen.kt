package br.com.fiap.llocalweb.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.generateSha256
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery
import br.com.fiap.locawebmailapp.utils.validateEmail
import br.com.fiap.locawebmailapp.utils.validatePassword

@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current

    var nome by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    val usuarioRepository = UsuarioRepository(context)
    val convidadoRepository = ConvidadoRepository(context)
    val passwordVisibility = remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(
            BitmapFactory.decodeResource(
                context.resources, R.drawable.user
            )
        )
    }

    val toastMessageAlreadyExistUser = stringResource(id = R.string.toast_already_exist_user)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            pickImageFromGallery(
                context = context,
                imageUri = imageUri,
                bitmap = bitmap
            )
        }

    val isErrorPassword = remember {
        mutableStateOf(false)
    }

    val isErrorEmail = remember {
        mutableStateOf(false)
    }

    val isErrorNome = remember {
        mutableStateOf(false)
    }
    val emailRepository = EmailRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)

    val toastMessageRegisterUserCreated = stringResource(id = R.string.toast_register_createduser)
    val welcomeSubject = stringResource(id = R.string.register_email_welcome)
    val hello = stringResource(id = R.string.register_email_hi)
    val messageFirstLine = stringResource(id = R.string.register_email_first)
    val messageSecondLine = stringResource(id = R.string.register_email_second)
    val messageThirdLine = stringResource(id = R.string.register_email_third)
    val messageFourthLine = stringResource(id = R.string.email_register_fourth)
    val messageFifthLine = stringResource(id = R.string.email_register_fifth)
    val messageSixthLine = stringResource(id = R.string.email_register_sixth)
    val messageSeventhLine = stringResource(id = R.string.email_register_seventh)
    val messageEighthLine = stringResource(id = R.string.email_register_eighth)
    val messageNinthLine = stringResource(id = R.string.email_register_ninth)
    val messageTenthLine = stringResource(id = R.string.email_register_tenth)
    val messageEleventhLine = stringResource(id = R.string.email_register_eleventh)
    val messageTwelfthLine = stringResource(id = R.string.email_register_twelfth)
    val messageNote = stringResource(id = R.string.email_register_note)
    val messageThirteenthLine = stringResource(id = R.string.email_register_thirteenth)
    val messageFourteenthLine = stringResource(id = R.string.email_register_fourteenth)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(5.dp))

        if (bitmap.value != null) {
            Image(
                bitmap = bitmap.value!!.asImageBitmap(),
                contentDescription = stringResource(id = R.string.content_desc_iconregister),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(shape = CircleShape)
            )

        }
        Button(
            onClick = {
                launcher.launch("image/*")
            },
            modifier = Modifier
                .height(56.dp)
                .width(150.dp)
                .padding(top = 12.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.lcweb_red_1)),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                stringResource(id = R.string.register_screen_pickimage),
                modifier = Modifier.align(Alignment.CenterVertically),
                color = colorResource(id = R.color.white),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

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
                    focusedBorderColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedBorderColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedLabelColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedLabelColor = colorResource(id = R.color.lcweb_red_1),
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorBorderColor = colorResource(id = R.color.lcweb_red_1),
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorLabelColor = colorResource(id = R.color.lcweb_red_1),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1)
                ),
                value = nome,
                onValueChange = {
                    isErrorNome.value = false
                    nome = it
                },
                label = { Text(stringResource(id = R.string.register_screen_fullname)) },
                placeholder = {
                    Text(stringResource(id = R.string.register_screen_insertfull))
                },
                textStyle = TextStyle(textDecoration = TextDecoration.None),
                singleLine = true,
                isError = isErrorNome.value

            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .width(330.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedBorderColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedLabelColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedLabelColor = colorResource(id = R.color.lcweb_red_1),
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorBorderColor = colorResource(id = R.color.lcweb_red_1),
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorLabelColor = colorResource(id = R.color.lcweb_red_1),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1)
                ),
                value = email,
                onValueChange = {
                    isErrorEmail.value = false
                    email = it
                },
                label = { Text(stringResource(id = R.string.register_screen_email)) },
                placeholder = {
                    Text(stringResource(id = R.string.register_screen_insertmail))
                },
                textStyle = TextStyle(textDecoration = TextDecoration.None),
                singleLine = true,
                isError = isErrorEmail.value

            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .width(330.dp),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedBorderColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedLabelColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedLabelColor = colorResource(id = R.color.lcweb_red_1),
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorBorderColor = colorResource(id = R.color.lcweb_red_1),
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorLabelColor = colorResource(id = R.color.lcweb_red_1),
                    focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1)
                ),
                value = senha,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        if (passwordVisibility.value) {
                            Icon(
                                painter = painterResource(id = R.drawable.eye_slash_regular),
                                contentDescription = stringResource(id = R.string.content_desc_eye_slash),
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.eye_regular),
                                contentDescription = stringResource(id = R.string.content_desc_eye),
                                Modifier.size(20.dp)
                            )
                        }
                    }
                },
                onValueChange = {
                    isErrorPassword.value = false
                    senha = it
                },
                label = { Text(stringResource(id = R.string.register_screen_pass)) },
                placeholder = {
                    Text(stringResource(id = R.string.register_screen_insertpass))
                },
                textStyle = TextStyle(textDecoration = TextDecoration.None),
                singleLine = true,
                isError = isErrorPassword.value
            )

            if (isErrorPassword.value || isErrorEmail.value || isErrorNome.value) {
                if (nome == "") {
                    Text(
                        text = stringResource(id = R.string.user_name_required),
                        color = colorResource(id = R.color.lcweb_red_1)
                    )

                } else if (email == "") {
                    Text(
                        text = stringResource(id = R.string.user_email_required),
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                } else if (senha == "") {
                    Text(
                        text = stringResource(id = R.string.user_password_required),
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                } else if (!validateEmail(email)) {
                    Text(
                        text = stringResource(id = R.string.user_email_format),
                        color = colorResource(id = R.color.lcweb_red_1),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )

                } else if (senha.length < 8) {
                    Text(
                        text = stringResource(id = R.string.user_password_length),
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                } else if (!validatePassword(password = senha)) {
                    Text(
                        text = stringResource(id = R.string.user_password_format),
                        color = colorResource(id = R.color.lcweb_red_1),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    passwordVisibility.value = true
                }
            }

            Button(
                onClick = {
                    isErrorNome.value = if (nome == "") true else false
                    isErrorEmail.value = if (email == "") true else false
                    isErrorPassword.value = if (senha == "") true else false

                    isErrorEmail.value = !validateEmail(email)
                    isErrorPassword.value = !validatePassword(password = senha)

                    if (!isErrorEmail.value && !isErrorPassword.value && !isErrorNome.value) {
                        val usuarioExistente = usuarioRepository.retornaUsarioPorEmail(email)

                        if (usuarioExistente == null) {
                            val hashPassword = generateSha256(senha)
                            val devUser = usuarioRepository.retornaUsarioPorEmail("dev@locaweb.com.br")
                            val usuario = Usuario(
                                nome = nome,
                                email = email,
                                senha = hashPassword,
                                profile_image = bitmapToByteArray(bitmap.value!!),
                                selected_user = false,
                                autenticado = false
                            )
                            val usuarioId = usuarioRepository.criarUsuario(usuario)
                            val usuarioCriado = usuarioRepository.retornaUsuarioPorId(usuarioId)

                            val convidadoExistente =
                                convidadoRepository.verificarConvidadoExiste(usuarioCriado.email)

                            if (convidadoExistente != usuarioCriado.email) {
                                val convidado = Convidado()
                                convidado.email = usuarioCriado.email
                                convidadoRepository.criarConvidado(convidado)
                            }



                            val emailWelcome = Email(
                                id_usuario = devUser.id_usuario,
                                remetente = "dev@locaweb.com.br",
                                destinatario = usuarioCriado.email,
                                assunto = welcomeSubject,
                                corpo = "$hello ${usuarioCriado.nome}!\n" +
                                        "$messageFirstLine\n" +
                                        "$messageSecondLine\n" +
                                        "$messageThirdLine \n" +
                                        "$messageFourthLine\n" +
                                        "$messageFifthLine\n" +
                                        "$messageSixthLine\n" +
                                        "$messageSeventhLine\n" +
                                        "$messageEighthLine\n" +
                                        "$messageNinthLine\n" +
                                        "$messageTenthLine\n" +
                                        "$messageEleventhLine\n" +
                                        "$messageTwelfthLine\n" +
                                        "$messageNote\n" +
                                        "$messageThirteenthLine\n" +
                                        messageFourteenthLine,
                                editavel = false,
                                enviado = true

                            )

                            val rowId = emailRepository.criarEmail(emailWelcome)
                            alteracaoRepository.criarAlteracao(
                                Alteracao(
                                    alt_id_email = rowId,
                                    alt_id_usuario = usuarioCriado.id_usuario
                                )
                            )
                            Toast.makeText(
                                context,
                                toastMessageRegisterUserCreated,
                                Toast.LENGTH_LONG
                            )
                                .show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, toastMessageAlreadyExistUser, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
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
                    text = stringResource(id = R.string.register_screen_signup),
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = stringResource(id = R.string.register_screen_return),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 30.dp, top = 10.dp)
                    .clickable { navController.popBackStack() },
                color = colorResource(id = R.color.lcweb_red_1),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}