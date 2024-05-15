package br.com.fiap.locawebmailapp.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R

@Composable
fun ModalNavDrawer(
    selectedDrawer: MutableState<String>,
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit,

    ) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(250.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.locaweb),
                    contentDescription = "",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)

                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_all_accounts),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "1",
                    onClick = {

                        if (navController.currentBackStackEntry?.destination?.route != "emailtodascontasscreen") {
                            selectedDrawer.value = "1"
                            navController.navigate("emailtodascontasscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.inbox_solid),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_main),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "2",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "emailmainscreen") {
                            selectedDrawer.value = "2"
                            navController.navigate("emailmainscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_sent),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "3",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "emailsenviadosscreen") {
                            selectedDrawer.value = "3"
                            navController.navigate("emailsenviadosscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_favorites),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "4",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "emailsfavoritosscreen") {
                            selectedDrawer.value = "4"
                            navController.navigate("emailsfavoritosscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pen_to_square_solid),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_drafts),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "5",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "emailseditaveisscreen") {
                            selectedDrawer.value = "5"
                            navController.navigate("emailseditaveisscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_days_regular),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_calendar),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "6",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "calendarmainscreen") {
                            selectedDrawer.value = "6"
                            navController.navigate("calendarmainscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.folder_open_solid),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_archive),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "7",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "emailsarquivadosscreen") {
                            selectedDrawer.value = "7"
                            navController.navigate("emailsarquivadosscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.exclamation_mark_filled),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_spam),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "9",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "emailsspamscreen") {
                            selectedDrawer.value = "9"
                            navController.navigate("emailsspamscreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )



                NavigationDrawerItem(
                    modifier = Modifier.padding(end = 5.dp),
                    shape = RoundedCornerShape(bottomEndPercent = 50, topEndPercent = 50),
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "",
                                modifier = Modifier
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.navbar_modal_bin),
                                modifier = Modifier.padding(start = 5.dp),
                                fontSize = 15.sp
                            )
                        }
                    },
                    selected = selectedDrawer.value == "8",
                    onClick = {
                        if (navController.currentBackStackEntry?.destination?.route != "emailslixeirascreen") {
                            selectedDrawer.value = "8"
                            navController.navigate("emailslixeirascreen")
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = colorResource(id = R.color.lcweb_gray_1),
                        selectedIconColor = colorResource(id = R.color.lcweb_red_1),
                        selectedTextColor = colorResource(id = R.color.lcweb_red_1),
                        unselectedIconColor = colorResource(id = R.color.lcweb_gray_1),
                        unselectedTextColor = colorResource(id = R.color.lcweb_gray_1)
                    )
                )
            }
        },
        content = content
    )
}