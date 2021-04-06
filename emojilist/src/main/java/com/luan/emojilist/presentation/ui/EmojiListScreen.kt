package com.luan.emojilist.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.luan.common.base.Resource
import com.luan.common.components.SwipeToRefreshLayout
import com.luan.common.domain.Emoji
import com.luan.common.helper.getViewModel
import com.luan.common.helper.loadPicture
import com.luan.emojilist.presentation.EmojiListViewModel
import java.util.*

@Composable
fun EmojiListScreen() {
    val viewModel = getViewModel<EmojiListViewModel>()
    val resourceState = viewModel.getListEmojiResource.observeAsState(initial = Resource.empty())

    resourceState.value?.let {
        when (it.status) {
            Resource.Status.SUCCESS,
            Resource.Status.CACHE -> EmojiListContent(viewModel = viewModel, listOfEmoji = it.data)
            Resource.Status.EMPTY -> viewModel.getList()
            else -> {
            }
        }
    }

}

@Composable
fun EmojiListContent(
    viewModel: EmojiListViewModel,
    listOfEmoji: List<Emoji>?
) {

    val list = remember { mutableStateListOf<Emoji>() }
    listOfEmoji?.let {
        list.addAll(it)
    }

    SwipeToRefreshLayout(
        refreshingState = false,
        onRefresh = { viewModel.getList() },
        refreshIndicator = {
            Surface(elevation = 10.dp, shape = CircleShape) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(4.dp)
                )
            }
        },
        content = { EmojiListComponent(list) },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiListComponent(listOfEmoji: SnapshotStateList<Emoji>) {

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 86.dp)
    ) {
        items(listOfEmoji) {
            EmojiListItem(
                item = it,
                onItemClicked = { item ->
                    listOfEmoji.remove(item)
                }
            )
        }
    }
}

@Composable
private fun EmojiListItem(
    item: Emoji,
    onItemClicked: (Emoji) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(4.dp)
            .height(86.dp)
            .width(86.dp)
            .shadow(6.dp)
            .clickable { onItemClicked.invoke(item) }
    ) {
        loadPicture(
            url = item.source,
            contentDescription = item.key,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )


    }
}