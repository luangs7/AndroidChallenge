package com.luan.common.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun <T> GridListWithSwipeRefresh(
    itemMinSize: Dp,
    isLoading: Boolean = false,
    onSwipeRefresh: () -> Unit,
    list: SnapshotStateList<T>,
    itemView: @Composable (T) -> Unit,
) {

    SwipeToRefreshLayout(
        refreshingState = isLoading,
        onRefresh = { onSwipeRefresh.invoke() },
        refreshIndicator = {
            Surface(elevation = 10.dp, shape = CircleShape) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(4.dp)
                )
            }
        },
        content = { GridLayoutView(itemMinSize,list, itemView) },
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <T> GridLayoutView(
    itemMinSize: Dp,
    list: SnapshotStateList<T>,
    itemView: @Composable (T) -> Unit) {

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = itemMinSize)
    ) {
        items(list) {
            itemView(it)
        }
    }
}