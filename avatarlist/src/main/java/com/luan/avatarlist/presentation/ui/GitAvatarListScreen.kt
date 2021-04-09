package com.luan.avatarlist.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.luan.avatarlist.domain.model.GitUser
import com.luan.avatarlist.presentation.GitUserViewModel
import com.luan.common.base.Resource
import com.luan.common.components.GridListWithSwipeRefresh
import com.luan.common.components.LoadingView
import com.luan.common.extension.observer
import com.luan.common.helper.getViewModel
import com.luan.common.helper.loadPicture

@Composable
fun GitAvatarListScreen() {
    val viewModel = getViewModel<GitUserViewModel>()
    val listState = viewModel.getAvatarList.observeAsState(Resource.empty())
    val deleteState = viewModel.deleteUser.observeAsState(Resource.empty())
    val isLoading = mutableStateOf(false)

    deleteState.observer(onEveryState = { isLoading.value = it == Resource.Status.LOADING })


    when (listState.value.status) {
        Resource.Status.SUCCESS,
        Resource.Status.CACHE ->
            GitAvatarList(
            onSwipeRefresh = { viewModel.getAvatarList() },
            onItemClickListener = { viewModel.deleteGitUser(it) },
            listUsers = listState.value.data
        )
        Resource.Status.EMPTY -> viewModel.getAvatarList()
        else -> { }
    }

    if (isLoading.value) {
        LoadingView()
    }
}


@Composable
private fun GitAvatarList(
    onSwipeRefresh: () -> Unit,
    onItemClickListener: (GitUser) -> Unit,
    listUsers: List<GitUser>?
) {

    val list = remember { mutableStateListOf<GitUser>() }
    listUsers?.let {
        list.clear()
        list.addAll(it)
    }

    GridListWithSwipeRefresh(
        itemMinSize = 96.dp,
        onSwipeRefresh = onSwipeRefresh,
        list = list
    ) {
        GitAvatarListItem(
            item = it,
            onItemClicked = { item ->
                onItemClickListener(item)
            },
        )
    }
}

@Composable
private fun GitAvatarListItem(
    item: GitUser,
    onItemClicked: (GitUser) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(4.dp)
            .height(96.dp)
            .width(96.dp)
            .shadow(6.dp)
            .clickable { onItemClicked.invoke(item) }
    ) {
        loadPicture(
            url = item.avatarUrl.orEmpty(),
            contentDescription = item.login.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )


    }
}