package com.luan.emojilist.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.luan.common.base.Resource
import com.luan.common.components.ButtonView
import com.luan.common.helper.getViewModel
import com.luan.common.helper.loadPicture
import com.luan.emojilist.R
import com.luan.emojilist.presentation.EmojiListViewModel

@Composable
fun EmojiHomeScreen(onNext: () -> Unit) {
    val viewModel = getViewModel<EmojiListViewModel>()
    Surface(contentColor = MaterialTheme.colors.background) {
        EmojiHomeContent(emojiViewModel = viewModel, onNextScreen = onNext)
    }
}

@Composable
fun EmojiHomeContent(emojiViewModel: EmojiListViewModel, onNextScreen: () -> Unit) {
    val randomState = emojiViewModel.getRandomEmojiResource.observeAsState(Resource.empty())
    val hasListState = emojiViewModel.hasEmoji.observeAsState().value ?: false

    randomState.value?.let {
        when (it.status) {
            Resource.Status.EMPTY -> emojiViewModel.getRandomEmoji()
            Resource.Status.SUCCESS,
            Resource.Status.CACHE -> {
                val url = it.data?.source
                EmojiHomeSuccessEmptyState(imageSource = url,
                    hasListStored = hasListState,
                    onButton1Clicked = {
                        emojiViewModel.onEmojiClick()
                    },
                    onNextScreen = onNextScreen)
            }
            Resource.Status.LOADING -> EmojiHomeLoading()
            else -> {
            }
        }
    }

}

@Composable
fun EmojiHomeSuccessEmptyState(
    imageSource: String? = null,
    hasListStored: Boolean = false,
    onButton1Clicked: () -> Unit,
    onNextScreen: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))
        imageSource?.let { url ->
            loadPicture(
                url = url,
                modifier = Modifier
                    .width(84.dp)
                    .height(84.dp),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin)))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonView(
                    modifier = Modifier.weight(5f),
                    label =
                    if (hasListStored) stringResource(id = R.string.random_emoji)
                    else stringResource(id = R.string.emoji_get),
                    onClickAction = { onButton1Clicked.invoke() })
                ButtonView(
                    modifier = Modifier.weight(5f),
                    label = stringResource(id = R.string.emoji_list),
                    onClickAction = { onNextScreen.invoke() })
            }
        }

    }
}

@Composable
private fun EmojiHomeLoading() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(84.dp)
                .height(84.dp),
        ) {
            CircularProgressIndicator(progress = 0.5f)
        }
    }

}