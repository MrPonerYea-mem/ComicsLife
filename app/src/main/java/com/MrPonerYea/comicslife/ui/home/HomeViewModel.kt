package com.MrPonerYea.comicslife.ui.home

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MrPonerYea.comicslife.adapter.RecyclerAdapter
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.AllArticles
import com.MrPonerYea.comicslife.data.pojo.Article
import com.MrPonerYea.comicslife.ui.activity.ArticleActivity
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
       // value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

   var articleList: MutableLiveData<List<Article>> = MutableLiveData()

    init {

    }

    fun updateArticleItems(items:List<Article>) {
        articleList.value = items
    }

}