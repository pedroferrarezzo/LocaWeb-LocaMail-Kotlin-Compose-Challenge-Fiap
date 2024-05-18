package br.com.fiap.locawebmailapp.screens.email

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.database.repository.AlteracaoRepository
import br.com.fiap.locawebmailapp.database.repository.AnexoRepository
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Anexo
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import br.com.fiap.locawebmailapp.utils.isValidEmail
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery
import br.com.fiap.locawebmailapp.utils.stringParaLista
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditaEmailScreen(navController: NavController, idEmail: Long) {

    val context = LocalContext.current


    val emailRepository = EmailRepository(context)
    val anexoRepository = AnexoRepository(context)
    val convidadoRepository = ConvidadoRepository(context)
    val usuarioRepository = UsuarioRepository(context)
    val alteracaoRepository = AlteracaoRepository(context)

    val email = emailRepository.listarEmailPorId(idEmail)
    val anexo = Anexo()
    val convidado = Convidado()
    val usuarioSelecionado = remember {
        mutableStateOf(usuarioRepository.listarUsuarioSelecionado())
    }

    val anexoArrayByteList = anexoRepository.listarAnexosArraybytePorIdEmail(idEmail)
    val bitmapList = remember {
        anexoArrayByteList.map {
            byteArrayToBitmap(it)
        }.toMutableStateList()
    }

    val destinatarios =
        remember { if (email.destinatario.equals("")) mutableStateListOf<String>() else stringParaLista(email.destinatario).toMutableStateList() }
    val destinatarioText = remember {
        mutableStateOf("")
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val assuntoText = remember {
        mutableStateOf(email.assunto)
    }

    val coporMailText = remember {
        mutableStateOf(email.corpo)
    }

    val cc = remember {
        mutableStateOf("")
    }
    val ccs =
        remember { if (email.cc.equals("")) mutableStateListOf<String>() else stringParaLista(email.cc).toMutableStateList() }


    val cco = remember {
        mutableStateOf("")
    }
    val ccos = remember {
        if (email.cco.equals("")) mutableStateListOf<String>() else stringParaLista(email.cco).toMutableStateList()
    }


    val isErrorPara = remember {
        mutableStateOf(false)
    }

    val isErrorCc = remember {
        mutableStateOf(false)
    }

    val isErrorCco = remember {
        mutableStateOf(false)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

            imageUri = uri
            pickImageFromGallery(
                context = context,
                imageUri = imageUri,
                bitmap = bitmap,
                bitmapList = bitmapList
            )
        }

    val expandedCc = remember {
        mutableStateOf(false)
    }

    if (email != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                            email.destinatario = if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                            email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                            email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                            email.assunto = assuntoText.value
                            email.corpo = coporMailText.value
                            email.enviado = false
                            email.editavel = true
                            anexo.id_email = email.id_email

                            emailRepository.atualizarEmail(email)
                            anexoRepository.excluirAnexoPorIdEmail(anexo.id_email)

                            if (bitmapList.isNotEmpty()) {
                                for (bitmap in bitmapList) {
                                    val byteArray = bitmapToByteArray(bitmap = bitmap)
                                    anexo.anexo = byteArray
                                    anexoRepository.criarAnexo(anexo)
                                }
                            }
                            Toast.makeText(context, "Rascunho salvo", Toast.LENGTH_LONG).show()
                            navController.popBackStack()
                        }
                        else {
                            Toast.makeText(context, "Ao menos um destinatário deve ser especificado", Toast.LENGTH_LONG).show()
                        }
                    }

                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "",
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.locaweb),
                    contentDescription = "",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)

                )

                Row {
                    IconButton(onClick = {
                        navController.popBackStack()
                        anexoRepository.excluirAnexoPorIdEmail(email.id_email)
                        emailRepository.excluirEmail(email = email)
                        Toast.makeText(context, "Rascunho excluído", Toast.LENGTH_LONG).show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "",
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }
                    IconButton(onClick = {
                        launcher.launch("image/*")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.paperclip_solid),
                            contentDescription = "",
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }

                    IconButton(onClick = {
                        if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                            email.destinatario = if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                            email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                            email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                            email.assunto = assuntoText.value
                            email.corpo = coporMailText.value
                            email.enviado = true
                            email.editavel = false
                            email.data = "${LocalDate.now()}"
                            email.horario =
                                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                            anexo.id_email = email.id_email

                            emailRepository.atualizarEmail(email)
                            anexoRepository.excluirAnexoPorIdEmail(anexo.id_email)

                            val todosDestinatarios = destinatarios + ccos + ccs

                            for (usuario in usuarioRepository.listarUsuarios()){
                                if (todosDestinatarios.contains(usuario.email)) {
                                    alteracaoRepository.criarAlteracao(
                                        Alteracao(
                                            alt_id_email = email.id_email,
                                            alt_id_usuario = usuario.id_usuario
                                        )
                                    )
                                }
                            }

                            val convidadoExistente =
                                convidadoRepository.verificarConvidadoExiste(email.destinatario)

                            if (convidadoExistente != email.destinatario) {
                                convidado.email = email.destinatario
                                convidadoRepository.criarConvidado(convidado)
                            }

                            if (bitmapList.isNotEmpty()) {
                                for (bitmap in bitmapList) {
                                    val byteArray = bitmapToByteArray(bitmap = bitmap)
                                    anexo.anexo = byteArray
                                    anexoRepository.criarAnexo(anexo)
                                }

                            }
                            Toast.makeText(context, "Email enviado", Toast.LENGTH_LONG).show()
                            navController.popBackStack()
                        }
                        else {
                            Toast.makeText(context, "Ao menos um destinatário deve ser especificado", Toast.LENGTH_LONG).show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "",
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )
                    }
                }

            }

            LazyRow {
                items(bitmapList) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                        )
                        IconButton(
                            onClick = {
                                bitmapList.remove(it)
                            },
                            modifier = Modifier.padding(top = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "",
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    }
                }
            }

            Row {
                Text(
                    text = "De:",
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.lcweb_red_1),
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = email.remetente,
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.lcweb_gray_1),
                    modifier = Modifier.padding(5.dp)
                )
            }
            HorizontalDivider(
                color = colorResource(id = R.color.lcweb_red_1)
            )

            TextField(
                value = destinatarioText.value,
                onValueChange = {
                    destinatarioText.value = it
                    if (isErrorPara.value) isErrorPara.value = false
                },
                leadingIcon = {
                    Text(
                        text = "Para",
                        modifier = Modifier.padding(10.dp),
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                },
                trailingIcon = {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            expandedCc.value = !expandedCc.value
                        }) {
                            Icon(
                                imageVector = if (!expandedCc.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                contentDescription = "",
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }

                        IconButton(onClick = {
                            if (destinatarios.contains(destinatarioText.value) || ccs.contains(
                                    destinatarioText.value
                                ) || ccos.contains(destinatarioText.value)
                            ) {
                                isErrorPara.value = true
                            } else {
                                isErrorPara.value = !isValidEmail(destinatarioText.value)
                            }

                            if (!isErrorPara.value) destinatarios.add(destinatarioText.value.toLowerCase())
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "",
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1),
                    errorContainerColor = Color.Transparent,
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                ),
                singleLine = true,
                textStyle = TextStyle(
                    textDecoration = TextDecoration.None
                ),
                isError = isErrorPara.value
            )

            LazyRow() {
                items(destinatarios) { destinatario ->

                    Button(
                        onClick = { destinatarios.remove(destinatario) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.lcweb_red_1),
                            contentColor = colorResource(id = R.color.white)
                        ),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.padding(horizontal = 5.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = destinatario, fontSize = 10.sp)
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 2.dp).width(15.dp).height(15.dp)
                            )
                        }
                    }
                }
            }

            if (expandedCc.value) {
                TextField(
                    value = cc.value,
                    onValueChange = {
                        cc.value = it
                        if (isErrorCc.value) isErrorCc.value = false
                    },
                    leadingIcon = {
                        Text(
                            text = "Cc",
                            modifier = Modifier.padding(10.dp),
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {

                            if (destinatarios.contains(cc.value) || ccs.contains(cc.value) || ccos.contains(cc.value)) {
                                isErrorCc.value = true
                            }
                            else {
                                isErrorCc.value = !isValidEmail(cc.value)
                            }
                            if (!isErrorCc.value) ccs.add(cc.value.toLowerCase())

                        }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "",
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.lcweb_red_1),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                        focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                        errorTextColor = colorResource(id = R.color.lcweb_red_1),
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                        errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                        errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                        errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                    ),
                    singleLine = true,
                    textStyle = TextStyle(
                        textDecoration = TextDecoration.None
                    ),
                    isError = isErrorCc.value
                )

                LazyRow() {
                    items(ccs) { cc ->

                        Button(
                            onClick = { ccs.remove(cc) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.lcweb_red_1),
                                contentColor = colorResource(id = R.color.white)
                            ),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.padding(horizontal = 5.dp),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = cc, fontSize = 10.sp)
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "",
                                    modifier = Modifier.padding(start = 2.dp).width(15.dp).height(15.dp)
                                )
                            }
                        }
                    }
                }



                TextField(
                    value = cco.value,
                    onValueChange = {
                        cco.value = it
                        if (isErrorCco.value) isErrorCco.value = false
                    },
                    leadingIcon = {
                        Text(
                            text = "Cco",
                            modifier = Modifier.padding(10.dp),
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (destinatarios.contains(cco.value) || ccs.contains(cco.value) || ccos.contains(cco.value)) {
                                isErrorCco.value = true
                            }
                            else {
                                isErrorCco.value = !isValidEmail(cco.value)
                            }
                            if (!isErrorCco.value) ccos.add(cco.value.toLowerCase())
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "",
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.lcweb_red_1),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                        focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                        focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                        unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                        errorTextColor = colorResource(id = R.color.lcweb_red_1),
                        errorContainerColor = Color.Transparent,
                        errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                        errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                        errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                        errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                    ),
                    singleLine = true,
                    textStyle = TextStyle(
                        textDecoration = TextDecoration.None
                    ),
                    isError = isErrorCco.value
                )

                LazyRow() {
                    items(ccos) { cco ->

                        Button(
                            onClick = { ccos.remove(cco) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.lcweb_red_1),
                                contentColor = colorResource(id = R.color.white)
                            ),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.padding(horizontal = 5.dp),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = cco, fontSize = 10.sp)
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "",
                                    modifier = Modifier.padding(start = 2.dp).width(15.dp).height(15.dp)
                                )
                            }

                        }
                    }
                }


            }

            TextField(
                value = assuntoText.value,
                onValueChange = { assuntoText.value = it },
                leadingIcon = {
                    Text(
                        text = "Assunto",
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.padding(10.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1),
                    errorContainerColor = Color.Transparent,
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                ),
                singleLine = true,
                textStyle = TextStyle(
                    textDecoration = TextDecoration.None
                )

            )

            TextField(
                value = coporMailText.value,
                onValueChange = { coporMailText.value = it },
                placeholder = { Text(text = "Escreva o e-email") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.lcweb_red_1),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedPlaceholderColor = colorResource(id = R.color.lcweb_gray_1),
                    focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_2),
                    focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                    unfocusedTextColor = colorResource(id = R.color.lcweb_gray_2),
                    errorTextColor = colorResource(id = R.color.lcweb_red_1),
                    errorContainerColor = Color.Transparent,
                    errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                    errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                    errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                    errorIndicatorColor = colorResource(id = R.color.lcweb_red_1)
                ),
                textStyle = TextStyle(
                    textDecoration = TextDecoration.None
                )
            )


        }
    }
}

