package br.com.fiap.locawebmailapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.llocalweb.screens.HomeScreen
import br.com.fiap.llocalweb.screens.LoginScreen
import br.com.fiap.llocalweb.screens.SignupScreen
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.screens.ai.AiResponseScreen
import br.com.fiap.locawebmailapp.screens.ai.AiScreen
import br.com.fiap.locawebmailapp.screens.calendar.CalendarMainScreen
import br.com.fiap.locawebmailapp.screens.calendar.CriaEventoScreen
import br.com.fiap.locawebmailapp.screens.calendar.CriaTarefaScreen
import br.com.fiap.locawebmailapp.screens.calendar.EditaEventoScreen
import br.com.fiap.locawebmailapp.screens.calendar.EditaTarefaScreen
import br.com.fiap.locawebmailapp.screens.email.CriaEmailScreen
import br.com.fiap.locawebmailapp.screens.email.CriaRespostaEmailScreen
import br.com.fiap.locawebmailapp.screens.email.EMailMainScreen
import br.com.fiap.locawebmailapp.screens.email.EditaEmailScreen
import br.com.fiap.locawebmailapp.screens.email.EditaRespostaEmailScreen
import br.com.fiap.locawebmailapp.screens.email.EmailTodasContasScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsArquivadosScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsEditaveisScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsEnviadosScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsExcluidosScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsFavoritosScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsPastaScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsSociaisScreen
import br.com.fiap.locawebmailapp.screens.email.EmailsSpamScreen
import br.com.fiap.locawebmailapp.screens.email.VisualizaEmailScreen
import br.com.fiap.locawebmailapp.ui.theme.LocaWebMailAppTheme
import br.com.fiap.locawebmailapp.utils.stringToLocalDate

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocaWebMailAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val usuarioRepository = UsuarioRepository(LocalContext.current)
                    val usuarios = usuarioRepository.listarUsuarios()
                    val screen =
                        if (usuarios.isEmpty()) {
                            "homescreen"
                        }
                        else if (usuarios.any{it.autenticado && it.selected_user}) {
                            "emailmainscreen"
                        }

                        else {
                            "loginscreen"
                        }

                    NavHost(
                        navController = navController,
                        startDestination = screen
                    ) {

                        composable(
                            route = "calendarmainscreen?data={data}",
                            arguments = listOf(
                                navArgument("data") {
                                    type = NavType.StringType
                                    nullable = true
                                },
                            ),
                        ) {
                            val data = it.savedStateHandle.get<String>("data")

                            if (data != null) {
                                CalendarMainScreen(
                                    navController = navController,
                                    stringToLocalDate(data)
                                )
                            } else {
                                CalendarMainScreen(navController = navController)
                            }
                        }

                        composable(
                            route = "criarespostaemailscreen?id_email={id_email}&id_resposta_email={id_resposta_email}",
                            arguments = listOf(
                                navArgument("id_email") {
                                    type = NavType.StringType
                                },
                                navArgument("id_resposta_email") {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) {
                            val idEmail = it.arguments?.getString("id_email")
                            val idRespostaEmail = it.arguments?.getString("id_resposta_email")
                            CriaRespostaEmailScreen(
                                navController = navController, idEmail!!.toLong(),
                                idRespostaEmail?.toLong()
                            )
                        }


                        composable(route = "criatarefascreen") {
                            CriaTarefaScreen(navController = navController)
                        }

                        composable(route = "homescreen") {
                            HomeScreen(navController = navController)
                        }

                        composable(route = "signupscreen") {
                            SignupScreen(navController = navController)
                        }

                        composable(route = "loginscreen") {
                            LoginScreen(navController = navController)
                        }

                        composable(route = "criaeventoscreen") {
                            CriaEventoScreen(navController = navController)
                        }

                        composable(route = "editatarefascreen/{id_agenda}") {
                            val idAgenda = it.arguments?.getString("id_agenda")
                            EditaTarefaScreen(
                                navController = navController,
                                id_agenda = idAgenda!!.toInt()
                            )
                        }

                        composable(route = "editaeventoscreen/{id_agenda}") {
                            val idAgenda = it.arguments?.getString("id_agenda")
                            EditaEventoScreen(
                                navController = navController,
                                id_agenda = idAgenda!!.toInt()
                            )
                        }

                        composable(route = "emailmainscreen") {
                            EMailMainScreen(navController = navController)
                        }

                        composable(route = "criaemailscreen") {
                            CriaEmailScreen(navController = navController)
                        }

                        composable(route = "emailsenviadosscreen") {
                            EmailsEnviadosScreen(navController = navController)
                        }

                        composable(route = "emailsfavoritosscreen") {
                            EmailsFavoritosScreen(navController = navController)
                        }

                        composable(route = "emailssociaisscreen") {
                            EmailsSociaisScreen(navController = navController)
                        }

                        composable(route = "aiscreen") {
                            AiScreen(navController = navController)
                        }

                        composable(route = "emailseditaveisscreen") {
                            EmailsEditaveisScreen(navController = navController)
                        }

                        composable(route = "emailslixeirascreen") {
                            EmailsExcluidosScreen(navController = navController)
                        }

                        composable(route = "emailspastascreen/{id_pasta}") {
                            val idPasta = it.arguments?.getString("id_pasta")
                            EmailsPastaScreen(
                                navController = navController,
                                id_pasta = idPasta!!.toLong()
                            )
                        }

                        composable(route = "airesponsescreen/{id_email}/{id_question}") {
                            val idEmail = it.arguments?.getString("id_email")
                            val idQuestion = it.arguments?.getString("id_question")
                            AiResponseScreen(
                                navController = navController,
                                id_email = idEmail!!.toLong(),
                                id_question = idQuestion!!.toLong()
                            )
                        }

                        composable(route = "emailsspamscreen") {
                            EmailsSpamScreen(navController = navController)
                        }

                        composable(route = "emailtodascontasscreen") {
                            EmailTodasContasScreen(navController = navController)
                        }

                        composable(route = "visualizaemailscreen/{id_email}/{is_todas_contas_screen}") {
                            val idEmail = it.arguments?.getString("id_email")
                            val todasContasScreen =
                                it.arguments?.getString("is_todas_contas_screen").toBoolean()
                            VisualizaEmailScreen(
                                navController = navController,
                                idEmail = idEmail!!.toLong(),
                                isTodasContasScreen = todasContasScreen
                            )
                        }

                        composable(route = "editarespostaemailscreen/{id_resposta_email}") {
                            val idRespostaEmail = it.arguments?.getString("id_resposta_email")
                            EditaRespostaEmailScreen(
                                navController = navController,
                                idRespostaEmail!!.toLong()
                            )
                        }

                        composable(route = "editaemailscreen/{id_email}") {
                            val idEmail = it.arguments?.getString("id_email")
                            EditaEmailScreen(navController = navController, idEmail!!.toLong())
                        }

                        composable(route = "emailsarquivadosscreen") {
                            EmailsArquivadosScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}












