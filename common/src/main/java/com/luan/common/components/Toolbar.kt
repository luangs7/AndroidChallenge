package com.luan.common.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(
    title: String,
    navigationIcon:ImageVector? = null,
    onNavigationIconClicked: (() -> Unit)? = null
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp,
        title = { Text(text = title) },
        navigationIcon = {
            navigationIcon?.let { icon ->
                IconButton(
                    onClick = { onNavigationIconClicked?.invoke() },
                ) {
                    Icon(imageVector = icon, contentDescription = "Voltar")
                }
            }
        }
    )
}