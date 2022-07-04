package com.rulhouse.composenavigationpage

import androidx.activity.OnBackPressedCallback
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

    val myChangePageThreshold = remember{ mutableStateOf(if (changePageThreshold > 1 || changePageThreshold < 0) 1 / 3f else changePageThreshold)}

    val width = remember { mutableStateOf(ScreenMethods.getWidth(context))}
    val widthDp = remember { mutableStateOf(ScreenMethods.convertPixelToDp(ScreenMethods.getWidth(context).toFloat(), context))}

    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    val nowIndex = remember { mutableStateOf(0)}

    val previousIsScrollProgress = remember { mutableStateOf(false) }

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
            if (listState.isScrollInProgress != previousIsScrollProgress.value) {
                previousIsScrollProgress.value = listState.isScrollInProgress
                // if changed to stop
                if (!listState.isScrollInProgress) {
                    // scroll to new index
                    coroutineScope.launch {
                        // set new index.
                        nowIndex.value = getNowIndex(
                            firstVisibleItemIndex = listState.firstVisibleItemIndex,
                            firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset,
                            nowIndex = nowIndex.value,
                            width = width.value,
                            changePageThreshold = myChangePageThreshold.value,
                        )
                        listState.animateScrollToItem(nowIndex.value)
                    }
                }
            }
            items(resList) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(
                            widthDp.value.dp
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
                nowIndex = nowIndex.value
            )
        }
    }
}

private fun getNowIndex(firstVisibleItemScrollOffset: Int, firstVisibleItemIndex: Int, width: Int, nowIndex: Int, changePageThreshold: Float): Int {
    return if (firstVisibleItemScrollOffset < width * changePageThreshold) {
        firstVisibleItemIndex
    } else if (firstVisibleItemScrollOffset > width * (1 - changePageThreshold)) {
        firstVisibleItemIndex + 1
    } else {
        if (firstVisibleItemIndex <= nowIndex) {
            firstVisibleItemIndex
        } else {
            firstVisibleItemIndex + 1
        }
    }
}