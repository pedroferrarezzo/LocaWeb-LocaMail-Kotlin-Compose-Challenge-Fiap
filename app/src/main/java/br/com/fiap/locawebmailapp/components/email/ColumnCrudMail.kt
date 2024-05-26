package br.com.fiap.locawebmailapp.components.email

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.Usuario

@Composable
fun ColumnCrudMail(
    modifier: Modifier = Modifier,
    usuarioSelecionado: MutableState<Usuario>,
    destinatarios: SnapshotStateList<String>,
    ccs: SnapshotStateList<String>,
    ccos: SnapshotStateList<String>,
    destinatarioText: MutableState<String>,
    cc: MutableState<String>,
    cco: MutableState<String>,
    assuntoText: MutableState<String>,
    corpoMailText: MutableState<String>,
    expandedCc: MutableState<Boolean>,
    isErrorPara: MutableState<Boolean>,
    isErrorCc: MutableState<Boolean>,
    isErrorCco: MutableState<Boolean>,
    onValueChangeTextFieldTo: (String) -> Unit,
    onClickExpandCc: () -> Unit,
    onClickAddDestinatario: () -> Unit,
    onClickRemoveDestinatario: (destinatario: String) -> Unit,
    onValueChangeTextFieldCc: (String) -> Unit,
    onClickAddCc: () -> Unit,
    onClickRemoveCc: (cc: String) -> Unit,
    onValueChangeTextFieldCco: (String) -> Unit,
    onClickAddCco: () -> Unit,
    onClickRemoveCco: (cco: String) -> Unit,
    onValueChangeTextFieldAssunto: (String) -> Unit,
    onValueChangeTextFieldCorpoMail: (String) -> Unit,
    isRespostaEmail: Boolean = false,
    showClearButtonAppear: (destinatario: String) -> Boolean = {false}
) {
    Row {
        Text(
            text = stringResource(id = R.string.mail_generic_from),
            fontSize = 15.sp,
            color = colorResource(id = R.color.lcweb_red_1),
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = usuarioSelecionado.value.email,
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
        onValueChange =
        onValueChangeTextFieldTo,
        leadingIcon = {
            Text(
                text = stringResource(id = R.string.mail_generic_to),
                modifier = Modifier.padding(10.dp),
                color = colorResource(id = R.color.lcweb_red_1)
            )
        },
        trailingIcon = {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onClickExpandCc) {
                    Icon(
                        imageVector = if (!expandedCc.value) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                        contentDescription = stringResource(id = R.string.content_desc_expand_area),
                        tint = colorResource(id = R.color.lcweb_gray_1)
                    )
                }

                IconButton(onClick = onClickAddDestinatario) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = stringResource(id = R.string.content_desc_add),
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
                onClick = { onClickRemoveDestinatario(destinatario) },
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
                    Text(
                        text = destinatario,
                        fontSize = 10.sp
                    )
                    if (isRespostaEmail) {
                        if (showClearButtonAppear(destinatario)) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(id = R.string.content_desc_clear),
                                modifier = Modifier
                                    .padding(start = 2.dp)
                                    .width(15.dp)
                                    .height(15.dp)
                            )
                        }
                    }
                    else {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(id = R.string.content_desc_clear),
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .width(15.dp)
                                .height(15.dp)
                        )
                    }
                }
            }
        }
    }

    if (expandedCc.value) {
        TextField(
            value = cc.value,
            onValueChange = {
                onValueChangeTextFieldCc(it)
            },
            leadingIcon = {
                Text(
                    text = stringResource(id = R.string.mail_generic_cc),
                    modifier = Modifier.padding(10.dp),
                    color = colorResource(id = R.color.lcweb_red_1)
                )
            },
            trailingIcon = {
                IconButton(onClick = onClickAddCc) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = stringResource(id = R.string.content_desc_add),
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
                    onClick = { onClickRemoveCc(cc) },
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
                            contentDescription = stringResource(id = R.string.content_desc_clear),
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .width(15.dp)
                                .height(15.dp)
                        )

                    }
                }
            }
        }

        TextField(
            value = cco.value,
            onValueChange = {
                onValueChangeTextFieldCco(it)
            },
            leadingIcon = {
                Text(
                    text = stringResource(id = R.string.mail_generic_cco),
                    modifier = Modifier.padding(10.dp),
                    color = colorResource(id = R.color.lcweb_red_1)
                )
            },
            trailingIcon = {
                IconButton(onClick = onClickAddCco) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = stringResource(id = R.string.content_desc_add),
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
                    onClick = { onClickRemoveCco(cco) },
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
                            contentDescription = stringResource(id = R.string.content_desc_clear),
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .width(15.dp)
                                .height(15.dp)
                        )
                    }
                }
            }
        }
    }

    TextField(
        value = assuntoText.value,
        onValueChange = onValueChangeTextFieldAssunto,
        leadingIcon = {
            Text(
                text = stringResource(id = R.string.mail_generic_subject),
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
        value = corpoMailText.value,
        onValueChange = onValueChangeTextFieldCorpoMail,
        placeholder = { Text(text = stringResource(id = R.string.mail_generic_body)) },
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