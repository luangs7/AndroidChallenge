package com.luan.challenge.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.luan.challenge.R
import com.luan.challenge.presentation.components.HomeContentView
import com.luan.common.components.Toolbar
import com.luan.common.ui.theme.AppTheme
import com.luan.navigation.BottomNavigationFakeItemsProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Preview
    @Composable
    fun Content() {
        AppTheme {
            HomeContentView(
                items = BottomNavigationFakeItemsProvider().values.toList(),
                onNavigationIconClicked = { this@MainActivity.onBackPressed() })
        }
    }
}