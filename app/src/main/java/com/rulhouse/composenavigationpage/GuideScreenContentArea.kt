package com.rulhouse.composenavigationpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rulhouse.composenavigationpage.IndexArea
import kotlinx.coroutines.launch


@Composable
fun GuideScreenContentArea(
    modifier: Modifier = Modifier,
    resList: List<Int>,
    changePageThreshold: Float = 1 / 3f
) {
    val context = LocalContext.current

    val myChangePageThreshold = if (changePageThreshold > 1 || changePageThreshold < 0) 1 / 3f else changePageThreshold

    val width = ScreenMethods.getWidth(context)
    val widthDp = ScreenMethods.convertPixelToDp(width.toFloat(), context)

    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    var nowIndex = 0

    val firstVisibleIndex = listState.firstVisibleItemIndex

    val firstVisibleItemOffset = listState.firstVisibleItemScrollOffset

    val previousIsScrollProgress = remember { mutableStateOf(false) }
    val isScrollInProgress = listState.isScrollInProgress

    // set new index.
    nowIndex = if (firstVisibleItemOffset < width * myChangePageThreshold) {
        firstVisibleIndex
    } else if (firstVisibleItemOffset > width * (1 - myChangePageThreshold)) {
        firstVisibleIndex + 1
    } else {
        if (firstVisibleIndex < nowIndex) {
            firstVisibleIndex
        } else {
            firstVisibleIndex + 1
        }
    }

    Column(
        modifier = modifier
            .background(Color.White),
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .weight(9f),

            ) {
            // if scrollInProgress state changed
            if (isScrollInProgress != previousIsScrollProgress.value) {
                previousIsScrollProgress.value = isScrollInProgress
                // if changed to stop
                if (!isScrollInProgress) {
                    // scroll to new index
                    coroutineScope.launch {
                        listState.animateScrollToItem(nowIndex)
                    }
                }
            }
            items(resList) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(
                            widthDp.dp
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        painter = painterResource(id = item),
                        contentDescription = ""
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            IndexArea(
                modifier = Modifier
                    .align(Alignment.Center),
                list = resList,
                nowIndex = nowIndex
            )
        }
    }
}