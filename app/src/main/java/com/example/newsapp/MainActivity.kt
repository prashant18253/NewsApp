package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.transition.Transition
import com.example.newsapp.api.NewsService
import com.example.newsapp.api.RetrofitHelper
import com.example.newsapp.repository.NewsRepo
import com.example.newsapp.viewModel.NewsViewModel
import com.example.newsapp.viewModel.NewsViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel
    lateinit var recyclerViewAdapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView()

        var newsService = RetrofitHelper.getInstance().create(NewsService::class.java)
        val repository = NewsRepo(newsService)
        newsViewModel = ViewModelProvider(this, NewsViewModelFactory(repository)).get(NewsViewModel::class.java)

        newsViewModel.getNews()
        newsViewModel.liveData.observe(this, Observer{
            Log.d("Hello", it.articles.toString())
        })

//        lifecycleScope.launch() {
//            newsViewModel.getNewsList().collectLatest {
//                recyclerViewAdapter.submitData(it)
//            }
//        }
//        itlifecycleScope.launch() {
//
//        }
        newsViewModel.getNewsList2().observe(this@MainActivity, Observer {
            recyclerViewAdapter.submitData(lifecycle,it)
        })


    }

    fun createRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration =DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = Adapter()
            adapter = recyclerViewAdapter
        }
    }
}