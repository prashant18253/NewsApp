package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.Models.Article

class Adapter : PagingDataAdapter<Article, Adapter.ViewHolders>(DiffUtilCallBack()) {
    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent,false)
        val viewHolders = ViewHolders(layoutInflater)
        return viewHolders
    }

    class ViewHolders(view: View) : RecyclerView.ViewHolder(view) {
        val newsTitle = view.findViewById<TextView>(R.id.news_title)
        val newsDescription = view.findViewById<TextView>(R.id.news_description)
        val newsImage = view.findViewById<ImageView>(R.id.news_image)

        fun bind(data: Article?) {
            newsDescription.setText(data?.description)
            newsTitle.setText(data?.title)

            Glide.with(newsImage)
                .load(data?.urlToImage)
                .into(newsImage)
        }
    }
    class DiffUtilCallBack : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.description == newItem.description && oldItem.title==newItem.title
        }
    }
}


