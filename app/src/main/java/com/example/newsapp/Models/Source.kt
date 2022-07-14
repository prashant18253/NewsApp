package com.example.newsapp.Models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Source(
    val id: String,
    val name: String
)