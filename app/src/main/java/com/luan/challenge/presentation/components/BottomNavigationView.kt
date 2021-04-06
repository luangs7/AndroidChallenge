package com.luan.challenge.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.luan.avatarlist.presentation.ui.GitAvatarHomeScreen
import com.luan.emojilist.presentation.ui.EmojiHomeScreen
import com.luan.emojilist.presentation.ui.EmojiListScreen
import com.luan.navigation.AVATAR_HOME
import com.luan.navigation.BottomNavigationScreens
import com.luan.navigation.EMOJI_HOME
import com.luan.navigation.InternalNavigationScreens


@Composable
fun BottomNavigationView(navController: NavHostController, items: List<BottomNavigationScreens>) {
    BottomNavigation {
        val currentRoute = currentRoute(navController = navController)
        items.onEach {
            BottomNavigationItem(
                selected = currentRoute == it.route,
                label = { Text(stringResource(id = it.label)) },
                icon = { Icon(it.icon,"") },
                onClick = {
                    if(currentRoute != it.route){
                        navController.navigate(it.route)
                    }
                })
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    return navController.currentBackStackEntryAsState().value?.arguments?.getString(KEY_ROUTE)
}

@Composable
fun isScreenHome(navController: NavHostController):Boolean =
    currentRoute(navController = navController)?.let {
        it == EMOJI_HOME || it == AVATAR_HOME
    } ?: false

@Composable
fun BottomScreenNavigationRoute(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomNavigationScreens.HomePage.route){
        composable(BottomNavigationScreens.HomePage.route){
            EmojiHomeScreen(
                onNext = {
                    navController.navigate(InternalNavigationScreens.EmojiListPage.route)
                }
            )
        }

        composable(BottomNavigationScreens.AvatarPage.route){
            GitAvatarHomeScreen()
        }

        composable(InternalNavigationScreens.EmojiListPage.route){
            EmojiListScreen()
        }
    }
}




