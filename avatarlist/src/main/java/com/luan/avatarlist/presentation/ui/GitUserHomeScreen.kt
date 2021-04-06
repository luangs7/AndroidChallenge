package com.luan.avatarlist.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luan.avatarlist.R
import com.luan.avatarlist.domain.model.GitUser
import com.luan.avatarlist.presentation.GitUserViewModel
import com.luan.common.base.Resource
import com.luan.common.components.ButtonView
import com.luan.common.components.LoadingView
import com.luan.common.extension.observer
import com.luan.common.helper.getViewModel


@Preview
@Composable
fun GitAvatarHomeScreen() {
    Surface(color = MaterialTheme.colors.background) {
        GitAvatarHomeContent()
    }
}


@Composable
fun GitAvatarHomeContent() {
    val viewModel = getViewModel<GitUserViewModel>()
    val loading = remember { mutableStateOf(false) }
    val userSaved = remember { mutableStateOf<GitUser?>(null) }
    val userState = viewModel.getGitUser.observeAsState(Resource.empty())
    val context = LocalContext.current

    val onSuccessAction: (GitUser?) -> Unit = { user ->
        userSaved.value = user
        user?.let {
            Toast.makeText(context, "${userSaved.value?.login} stored.", Toast.LENGTH_SHORT).show()
        }

    }

    userState.observer(
        onCache = { onSuccessAction.invoke(it) },
        onSuccess = { onSuccessAction.invoke(it) },
        onEveryState = {
            loading.value = it == Resource.Status.LOADING
        }
    )

    Box(contentAlignment = Alignment.Center) {
        Column {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_large)))
            SearchView {
                viewModel.setQueryValue(it)
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin)))
            ButtonView(
                label = stringResource(id = R.string.avatar_list)
            ) {

            }
            ButtonView(
                label = stringResource(id = R.string.google_list)
            ) {

            }
        }
        if (loading.value) {
            LoadingView()
        }
    }

}


@Composable
fun SearchView(
    onSearchClick: (String) -> Unit
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(8f),
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text(text = "Digite o usuário") },
            singleLine= true,
            keyboardActions= KeyboardActions(onSearch = { onSearchClick.invoke(textState.value.text) })

        )
        IconButton(
            modifier = Modifier.weight(2f),
            onClick = { onSearchClick.invoke(textState.value.text) }
        ) { Icon(imageVector = Icons.Filled.Search, contentDescription = "") }
    }
}