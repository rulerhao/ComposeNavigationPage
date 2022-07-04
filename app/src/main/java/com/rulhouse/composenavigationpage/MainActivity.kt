package com.rulhouse.composenavigationpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
//                LazyRow() {
//                    items(list) { item ->
//
//                        Image(
//                            modifier = Modifier
//                                .size(1000.dp),
//                            painter = painterResource(id = item),
//                            contentDescription = null
//                        )
//                    }
//                }
                GuideScreenContentArea(
                    resList = list,
                    changePageThreshold = 1 / 3f
                )
            }
        }
    }
}