package com.luan.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

const val EMOJI_HOME = "home"
const val AVATAR_HOME = "avatarHome"
const val EMOJI_LIST = "emojiList"

class BottomNavigationFakeItemsProvider: PreviewParameterProvider<BottomNavigationScreens> {
    override val values = sequenceOf(BottomNavigationScreens.HomePage,BottomNavigationScreens.AvatarPage)
    override val count: Int = values.count()
}

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    object HomePage : BottomNavigationScreens(EMOJI_HOME, R.string.home_tab, Icons.Filled.Terrain)
    object AvatarPage : BottomNavigationScreens(AVATAR_HOME, R.string.list_avatar, Icons.Filled.Terrain)
}

sealed class InternalNavigationScreens(
    val route: String,
    val parentRoute: String
    ) {
    object EmojiListPage : InternalNavigationScreens(route= EMOJI_LIST, parentRoute = EMOJI_HOME)
    object AvatarPage : InternalNavigationScreens(route= AVATAR_HOME, parentRoute = EMOJI_HOME)
}
