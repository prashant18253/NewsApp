package com.example.newsapp.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Articles")
data class Article(
    val author: String,
    @PrimaryKey
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String)