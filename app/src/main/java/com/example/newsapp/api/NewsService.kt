package com.example.newsapp.api

import com.example.newsapp.Models.NewsList
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything?q=tesla&apiKey=7718e0b093dc4528b338f3d48989d20a")
    suspend fun getNews(@Query("page") page:Int = 1, @Query("pageSize")pageSize:Int = 10) : NewsList
}