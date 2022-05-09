package com.rulhouse.composenavigationpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rulhouse.composenavigationpage.ui.theme.ComposeNavigationPageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationPageTheme {
                val list = listOf(
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_foreground,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_foreground,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_foreground,
                )
                GuideScreenContentArea(
                    resList = list,
                    changePageThreshold = 1 / 3f
                )
            }
        }
    }
}