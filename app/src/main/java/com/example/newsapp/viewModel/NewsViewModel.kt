package com.example.newsapp.viewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.Models.Article
import com.example.newsapp.Models.NewsList
import com.example.newsapp.api.NewsService
import com.example.newsapp.api.RetrofitHelper
import com.example.newsapp.repository.ArticlePagingSource
import com.example.newsapp.repository.NewsRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val repo: NewsRepo) : ViewModel(){
    var liveData = MutableLiveData<NewsList>()
    lateinit var newsService: NewsService
    init{
        newsService = RetrofitHelper.getInstance().create(NewsService::class.java)
    }
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    fun getNews(page:Int =1 , pageSize:Int =10){
        viewModelScope.launch(coroutineExceptionHandler){
            withContext(Dispatchers.IO){
                val newsList = repo.getNews()
                liveData.postValue(newsList)
            }
        }
    }


//    fun getNewsList2() : LiveData<PagingData<Article>>{
//        return Pager(config = PagingConfig(pageSize = 10, maxSize = 50), pagingSourceFactory = {repo.getNewsList()})
//            .flow
//            .cachedIn(viewModelScope).asLiveData()
//    }


    suspend fun getNewsList3() : LiveData<PagingData<Article>>{
        return repo.getNewsList3().cachedIn(viewModelScope).asLiveData()
    }
}