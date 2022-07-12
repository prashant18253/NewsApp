package com.example.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.NewsList
import com.example.newsapp.api.NewsService
import kotlinx.coroutines.flow.Flow

class NewsRepo(private val newsService: NewsService) {

    fun getNewsList() = ArticlePagingSource(newsService);

    fun getNewsList2() : Flow<PagingData<Article>>{
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 100),
            pagingSourceFactory = {ArticlePagingSource(newsService)})
            .flow
    }
    suspend fun getNews(page:Int =1, pageSize: Int = 10) : NewsList{
        return newsService.getNews(page, pageSize)
    }

}