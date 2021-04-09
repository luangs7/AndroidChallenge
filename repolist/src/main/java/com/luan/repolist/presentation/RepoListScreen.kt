package com.luan.repolist.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import com.luan.common.base.Resource
import com.luan.common.components.LoadingView
import com.luan.common.extension.observer
import com.luan.common.helper.getViewModel
import com.luan.common.helper.loadPicture
import com.luan.common.ui.theme.Shapes
import com.luan.navigation.BottomNavigationScreens
import com.luan.repolist.domain.model.Repository

@Composable
fun RepoListScreen() {
    val viewModel: RepoListViewModel = getViewModel()
    val listState = viewModel.getRepositoryResponse.observeAsState(initial = Resource.empty())

    when (listState.value.status) {
        Resource.Status.SUCCESS,
        Resource.Status.CACHE -> Surface {
            RepoListView(
                onLoadMore = { viewModel.getList() },
                data = listState.value.data!!
            )
        }
        Resource.Status.EMPTY -> viewModel.getList()
        else -> {
        }
    }


}

@Composable
private fun RepoListView(
    onLoadMore: () -> Unit,
    data: List<Repository>
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(data) { index, item ->
//            if (data.isNotEmpty() && data.lastIndex == index) {
//                onLoadMore()
//            }
            RepoListItem(item)
        }
//        if (loading.value) item { RepoListItemLoading() }
    }


}


@Composable
private fun RepoListItem(item: Repository) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp)
    ) {
        ConstraintLayout {
            val (roundedBox, name) = createRefs()
            if (item.private) {
                Box(modifier = Modifier
                    .constrainAs(roundedBox) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .border(1.dp, Color.Red, MaterialTheme.shapes.medium)) {

                    Text(text = "Privado", modifier = Modifier.padding(6.dp))
                }
            }
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .constrainAs(name) {
                        top.linkTo(roundedBox.bottom)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }, text = item.fullName.orEmpty()
            )
        }
    }
}

@Composable
private fun RepoListItemLoading() {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp)
    ) {
        ConstraintLayout {
            val circularLoading = createRef()

            CircularProgressIndicator(
                progress = 0.5f,
                modifier = Modifier
                    .constrainAs(circularLoading) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .padding(16.dp))

        }
    }
}