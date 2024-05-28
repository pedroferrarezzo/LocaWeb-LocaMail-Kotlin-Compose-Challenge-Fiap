package br.com.fiap.locawebmailapp.screens.ai

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import br.com.fiap.locawebmailapp.BuildConfig
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.database.repository.AiQuestionRepository
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.model.ai.ContentsBody
import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import br.com.fiap.locawebmailapp.model.ai.TextRequestResponse
import br.com.fiap.locawebmailapp.utils.callGemini
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import com.halilibo.richtext.commonmark.CommonmarkAstNodeParser
import com.halilibo.richtext.commonmark.MarkdownParseOptions
import com.halilibo.richtext.markdown.BasicMarkdown
import com.halilibo.richtext.ui.RichTextScope

@Composable
fun AiResponseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    id_email: Long,
    id_question: Long
) {
    val context = LocalContext.current
    val emailRepository = EmailRepository(context)
    val email = emailRepository.listarEmailPorId(id_email)
    val aiQuestionRepository = AiQuestionRepository(context)
    val question = aiQuestionRepository.listarPergunta(id_question, id_email)

    val isLoading = remember {
        mutableStateOf(true)
    }

    val geminiResponse = remember {
        mutableStateOf(GeminiResponse())
    }

    val isConnectedStatus = remember {
        mutableStateOf(checkInternetConnectivity(context))
    }

    val isError = remember {
        mutableStateOf(false)
    }

    val toastMessageAiWait = stringResource(id = R.string.toast_mail_ai_wait)
    val aiLanguage = stringResource(id = R.string.ai_language)

    LaunchedEffect(key1 = Unit) {
        try {
            isConnectedStatus.value = checkInternetConnectivity(context)
            if (!isConnectedStatus.value) {
                isLoading.value = false
            } else {
                val geminiRequest = GeminiRequest()
                val textRequestResponse = TextRequestResponse()
                val parts = ContentsBody()
                val apiToken = BuildConfig.API_KEY

                textRequestResponse.text =
                "Pergunta: ${question.pergunta}\n" +
                        "Email:\n"+
                        "De: ${email.remetente}\n" +
                        "Para: ${email.destinatario}\n" +
                        "Cc: ${email.cc}\n" +
                        "Cco: ${email.cco}\n" +
                        "Assunto: ${email.assunto}\n" +
                        "Corpo: ${email.corpo}\n" +
                        "\n" +
                        "**OBSERVAÇÃO: \n" +
                        "- Sua resposta deve ser baseada no email acima;\n" +
                        "- Sua resposta deve ser fornecida no idioma: $aiLanguage;\n" +
                        "**Qualquer pergunta que fuja do contexto do email (assunto e corpo) deve ser ignorada e respondida com uma resposta padrão**.\n"
                parts.parts = listOf(textRequestResponse)
                geminiRequest.contents = listOf(parts)
                geminiResponse.value =
                    callGemini(apiToken, geminiRequest)

                if (geminiResponse.value != null) {
                    isLoading.value = false
                }

            }
        } catch (t: Throwable) {
            isError.value = true
            isLoading.value = false
        }
    }

    if (isLoading.value) {
        BackHandler {
            Toast.makeText(
                context,
                toastMessageAiWait,
                Toast.LENGTH_LONG
            ).show()
        }

        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp, 100.dp),
                color = colorResource(id = R.color.lcweb_red_1)
            )
        }
    } else {
        if (!isConnectedStatus.value) {
            Box {
                ErrorComponent(
                    title = stringResource(id = R.string.ai_error_oops),
                    subtitle = stringResource(id = R.string.ai_error_verifynet),
                    painter = painterResource(id = R.drawable.notfound),
                    descriptionimage = stringResource(id = R.string.content_desc_nonet),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(400.dp, 400.dp),
                    modifierButton = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .height(50.dp)
                        .align(Alignment.BottomCenter),
                    textButton = stringResource(id = R.string.ai_button_return),
                    buttonChange = {
                        navController.popBackStack()
                    }
                )
            }
        } else if (isError.value) {
            Box {
                ErrorComponent(
                    title = stringResource(id = R.string.ai_error_oops),
                    subtitle = stringResource(id = R.string.ai_error_apiproblem),
                    painter = painterResource(id = R.drawable.bugfixing),
                    descriptionimage = stringResource(id = R.string.content_desc_apiproblem),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(400.dp, 400.dp),
                    modifierButton = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .height(50.dp)
                        .align(Alignment.BottomCenter),
                    textButton = stringResource(id = R.string.ai_button_return),
                    buttonChange = {
                        navController.popBackStack()
                    }
                )
            }
        } else {
            Box {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp)
                        .align(Alignment.TopCenter)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.aiworking),
                        contentDescription = stringResource(id = R.string.content_desc_ai_working),
                        modifier = Modifier
                            .width(130.dp)
                            .height(130.dp)
                    )
                    Text(
                        text = "${stringResource(id = R.string.ai_analyse_first)} \n ${
                            stringResource(
                                id = R.string.ai_analyse_second
                            )
                        }",
                        fontSize = 30.sp,
                        color = colorResource(id = R.color.lcweb_gray_1),
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 10.dp)
                ) {
                    Box(
                        modifier
                            .background(color = colorResource(id = R.color.white), shape = RoundedCornerShape(10.dp))
                            .border(
                                width = 2.dp,
                                color = colorResource(id = R.color.lcweb_red_1),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .height(300.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                ) {
                                    val parser = CommonmarkAstNodeParser(
                                        options = MarkdownParseOptions(true)
                                    )
                                    val astNode = parser.parse(
                                        geminiResponse.value.candidates.first().content.parts.first().text
                                    )
                                    RichTextScope.BasicMarkdown(astNode)
                                }

                            }
                        }
                    }
                }

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = colorResource(id = R.color.lcweb_red_1)
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .height(50.dp)
                        .align(Alignment.BottomCenter),
                    elevation = ButtonDefaults.buttonElevation(6.dp)

                ) {
                    Text(
                        text = stringResource(id = R.string.ai_button_return),
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}