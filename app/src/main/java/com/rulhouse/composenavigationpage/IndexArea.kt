package com.rulhouse.composenavigationpage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RadioButtonChecked
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun IndexArea(
    modifier: Modifier,
    list: List<Int>,
    nowIndex: Int
) {
    LazyRow(
        modifier = modifier
            .wrapContentSize(),
    ) {
        itemsIndexed(list) { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center),
                    imageVector = if (nowIndex == index) Icons.Rounded.RadioButtonChecked
                    else Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = "page index",
                    tint = Color.Black
                )
            }
        }
    }
}