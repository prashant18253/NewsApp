package com.example.newsapp.repository

import android.util.Log
import androidx.paging.*
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.NewsList
import com.example.newsapp.api.NewsService
import com.example.newsapp.db.ArticleDAO
import com.example.newsapp.db.ArticleDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NewsRepo(private val newsService: NewsService, private val articleDAO: ArticleDAO) : RepoCallBack{

    var page: Int = 1
    fun getNewsList() = ArticlePagingSource(newsService, articleDAO, this);

    suspend fun getNewsList3() : Flow<PagingData<Article>>{
//        withContext(Dispatchers.IO){
//            updateArticles(1)
//        }
        withContext(Dispatchers.IO) {
            val article= articleDAO.getArticles()
            Log.d("Repo", "Size of data" + article.size)
        }
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 50),
            pagingSourceFactory = {ArticlePagingSource(newsService, articleDAO, this)})
            .flow
    }


    suspend fun updateArticles(page:Int){
        try{
            //Log.d("Repo", "Data updated page -$page")
            val response = newsService.getNews(page = page)
            if(!response?.articles.isEmpty()){
                //Log.d("Repo", response.articles.toString())
                //articleDAO.clearAll()
                articleDAO.insertAll(response.articles)
                Log.d("Repo", "Repo Page- $page"+articleDAO.getArticles().toString())
            }
        }
        catch(e: Exception){
        }
    }

    override suspend fun getKeys(nextKey: Int?, prevKey:Int?) {
        Log.d("Repo", "Keys : NextKey = $nextKey, PrevKey = $prevKey")
        if(nextKey == null || nextKey%5 ==0 ) {
            updateArticles(++page)
        }
        else if(prevKey==null && page>1){
            updateArticles(--page)
        }
        else {
            page =1
            updateArticles(page)
        }
    }

    suspend fun getNews(page:Int =1, pageSize: Int = 10) : NewsList{
        return newsService.getNews(page, pageSize)
    }




}