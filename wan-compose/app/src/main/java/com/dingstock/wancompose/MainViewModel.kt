package com.dingstock.wancompose

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dingstock.core.model.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private var pagingSource = ArticlePagingSource(repository)
    private val pagingConfig = PagingConfig(pageSize = 20)
    val pager = Pager(pagingConfig, pagingSourceFactory = { pagingSource })

    fun refresh() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    val banner = async(Dispatchers.IO) { repository.banner() }.await()
                    val topArticles = async(Dispatchers.IO) { repository.topArticles() }.await()
                } catch (e: Exception) {
                    Log.e(TAG, e.message, e)
                }
            }
        }
    }
}

class ArticlePagingSource(private val repository: Repository) : PagingSource<Int, ArticleEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        return try {
            val page = params.key ?: 0
            val articles = repository.articles(page)
            LoadResult.Page(
                data = articles.datas,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (articles.over) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
        return null
    }
}