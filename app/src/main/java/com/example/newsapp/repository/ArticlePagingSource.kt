package com.example.newsapp.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.Models.Article
import com.example.newsapp.api.NewsService
import com.example.newsapp.db.ArticleDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ArticlePagingSource(private val newsService: NewsService, private val articleDAO: ArticleDAO, val repoCallBack: RepoCallBack) : PagingSource<Int, Article>() {
    val nextPos = 0
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try{
            val nextPage:Int = params.key ?: 0
            //val response = newsService.getNews(page = nextPage)
            val responseDAO = articleDAO.getArticles(5, nextPage)

            Log.d("Repo", "Repo "+ responseDAO.toString())

            var loadResult = LoadResult.Page(
                data = responseDAO,
                prevKey = if(nextPage==0) null else nextPage-5,
                nextKey = if(responseDAO.isEmpty()) null else nextPage + 5
            )
//            loadResult?.nextKey?.let { loadResult?.prevKey?.let { it1 ->
//                repoCallBack.getKeys(it,
//                    it1
//                )
//            } }
            repoCallBack.getKeys(loadResult.nextKey, loadResult.prevKey)
            return loadResult
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}