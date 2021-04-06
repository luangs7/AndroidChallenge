package com.luan.challenge.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.compose.rememberNavController
import com.luan.common.components.Toolbar
import com.luan.navigation.BottomNavigationFakeItemsProvider
import com.luan.navigation.BottomNavigationScreens


@PreviewParameter(provider = BottomNavigationFakeItemsProvider::class)
@Composable
fun HomeContentView(items: List<BottomNavigationScreens>) {
    val navController = rememberNavController()
    val isHome = remember { mutableStateOf<Boolean>(true) }

    isHome.value = isScreenHome(navController = navController)

    Scaffold(
        topBar = {
            Toolbar(
                title = "Android Challenge",
                navigationIcon = if (isHome.value.not()) {
                    null
                } else {
                    Icons.Filled.ArrowBack
                },
            )
        },
        bottomBar = { BottomNavigationView(navController = navController, items = items) },
    ) {
        BottomScreenNavigationRoute(navController = navController)
    }
}