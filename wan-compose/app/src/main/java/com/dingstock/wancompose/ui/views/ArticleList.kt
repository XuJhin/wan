package com.dingstock.wancompose.ui.views

import android.text.Html
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.dingstock.core.model.ArticleEntity
import com.dingstock.wancompose.ui.theme.color9FA5B3
import com.dingstock.wancompose.ui.theme.text25262A

@Composable
fun ArticleItem(item: ArticleEntity) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString(),
            modifier = Modifier.fillMaxWidth(),
            color = text25262A,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(item.author, color = text25262A, fontSize = 12.sp)
            Text(item.niceDate, color = color9FA5B3, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            color = color9FA5B3,
            thickness = 0.2.dp,
        )
    }
}

@Composable
fun ArticleList(pager: Pager<Int, ArticleEntity>) {
    val lazyPagingItems = pager.flow.collectAsLazyPagingItems()
    val state: LazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(count = lazyPagingItems.itemCount, key = lazyPagingItems.itemKey { it.id }) { index ->
            val item = lazyPagingItems[index] ?: return@items
            ArticleItem(item)
        }
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Error -> {
            }

            LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp), text = "加载中......"
                        )
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            is LoadState.NotLoading -> {
            }
        }
        when (lazyPagingItems.loadState.append) { // Pagination
            is LoadState.Error -> { //TODO Pagination Error Item
                //state.error to get error message
            }

            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "分页加载中......")
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            else -> {}
        }
    }
}