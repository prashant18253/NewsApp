package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.Models.Article

@Database(entities = [Article::class], exportSchema = false, version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDAO() : ArticleDAO
    companion object{

        @Volatile
        private var Instance : ArticleDatabase?= null

        fun getDatabase(context : Context) : ArticleDatabase {
            if(Instance == null){
                Instance = Room.databaseBuilder(context, ArticleDatabase::class.java, "article_database").build()
            }
            return Instance!!
        }
    }
}