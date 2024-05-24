package br.com.fiap.locawebmailapp.screens.ai

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import br.com.fiap.locawebmailapp.database.repository.EmailRepository
import br.com.fiap.locawebmailapp.model.ai.ContentsBody
import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import br.com.fiap.locawebmailapp.model.ai.TextRequestResponse
import br.com.fiap.locawebmailapp.utils.callGemini
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.retornaApiToken
import com.halilibo.richtext.commonmark.CommonmarkAstNodeParser
import com.halilibo.richtext.commonmark.MarkdownParseOptions
import com.halilibo.richtext.markdown.BasicMarkdown
import com.halilibo.richtext.ui.RichTextScope

@Composable
fun AiResponseScreen(modifier: Modifier = Modifier, navController: NavController, id_email: Long) {

    val context = LocalContext.current
    val emailRepository = EmailRepository(context)
    val email = emailRepository.listarEmailPorId(id_email)

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
                    "Forneça um resumo sobre o email abaixo. Destaque os pontos principais.\n" +
                            email.corpo
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
                "Aguarde a análise de nossa assistente virtual",
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
                    modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp).align(Alignment.TopCenter)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.aiworking),
                        contentDescription = "",
                        modifier = Modifier
                            .width(130.dp)
                            .height(130.dp)
                    )
                    Text(
                        text = "Analisei o email \n para você:",
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
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 10.dp)
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