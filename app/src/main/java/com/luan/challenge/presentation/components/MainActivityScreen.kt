package com.luan.challenge.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.luan.common.components.Toolbar
import com.luan.navigation.BottomNavigationFakeItemsProvider
import com.luan.navigation.BottomNavigationScreens


@PreviewParameter(provider = BottomNavigationFakeItemsProvider::class)
@Composable
fun HomeContentView(
    items: List<BottomNavigationScreens>,
    onNavigationIconClicked: () -> Unit
) {
    val navController = rememberNavController()
    val isHome = remember { mutableStateOf<Boolean>(true) }

    isHome.value = isScreenHome(navController = navController)

    Scaffold(
        topBar = {
            Toolbar(
                title = "Android Challenge",
                navigationIcon = if (isHome.value) {
                    null
                } else {
                    Icons.Filled.ArrowBack
                },
                onNavigationIconClicked = onNavigationIconClicked
            )
        },
        bottomBar = { BottomNavigationView(navController = navController, items = items) },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomScreenNavigationRoute(navController = navController)
        }
    }

}