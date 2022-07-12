package com.example.newsapp.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.Models.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles : List<Article>)

    @Query("SELECT * FROM articles LIMIT :size")
    fun getArticles(size: Int): PagingSource<Int, Article>

    @Query("DELETE FROM articles")
    suspend fun clearAll()
}