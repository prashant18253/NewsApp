package com.example.newsapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.Models.Article
import com.example.newsapp.api.NewsService

class ArticlePagingSource(private val newsService: NewsService) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try{
            val nextPage:Int = params.key ?: 1
            val response = newsService.getNews(page = nextPage)
            //response.
            return LoadResult.Page(
                data = response.articles,
                prevKey = if(nextPage==1) null else nextPage-1,
                nextKey = if(response?.articles == null) null else nextPage + 1
            )
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}