package com.example.newsapp.repository

import androidx.paging.PagingSource
import com.example.newsapp.Models.Article

interface RepoCallBack {
    suspend fun getKeys(nextkey: Int?, prevKey:Int?)
}