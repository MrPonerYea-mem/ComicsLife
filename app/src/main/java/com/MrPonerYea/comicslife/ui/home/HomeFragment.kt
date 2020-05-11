package com.MrPonerYea.comicslife.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.adapter.RecyclerAdapter
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.AllArticles
import com.MrPonerYea.comicslife.data.pojo.Article
import com.MrPonerYea.comicslife.data.pojo.ItemRecycler
import com.MrPonerYea.comicslife.ui.activity.ArticleActivity
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var myAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

        homeViewModel.articleList.observe(viewLifecycleOwner, object : Observer<List<Article>?> {
            override fun onChanged(t: List<Article>?) {
                if (t != null)
                    myAdapter.refreshUsers(t)
            }
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Рабочая система, закомментил для тестов
        val articleItems: List<Article> = listOf(Article())
        getArticleItems(articleItems)

        //var runnable: Runnable? = null
        handler = Handler()
        swipe_refresh_layout.setOnRefreshListener {
            runnable = Runnable {
                Toast.makeText(this.context, "+++", id).show()
                getArticleItems(articleItems)
                swipe_refresh_layout.isRefreshing = false
            }

            handler.postDelayed(runnable, 1500.toLong())
        }

        swipe_refresh_layout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light
        )


        // Равботает сделать статьи так
        /*web_view_article.loadDataWithBaseURL(null, "<h1>Hello</h1><p>test</p> <sdf> sdfsdf</sds> <ul> <li>hhhhhhhhhh </li> </ul> <img src=\"https://img-s1.onedio.com/id-540da08efbb2a0c252067682/rev-1/raw/s-749c0330f908406b8e10946f581e6c8c1dfee43d.jpg\" width=\"189\" height=\"255\" >", "text/html", "utf-8", null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text_view_img.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p> <ul> <li>hhhhhhhhhh </li> </ul>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            text_view_img.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p> <ul> <li>hhhhhhhhhh </li> </ul>"));
        }*/

//        swipe_refresh_layout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
//            override fun onRefresh() {
//                Toast.makeText(context, "+++", id).show()
//                swipe_refresh_layout.isRefreshing = false
//            }
//        })
    }

    private fun getArticleItems(articleItems: List<Article>) {
        var articleItems1 = articleItems
        NetworkService.getInstance()
            ?.getJSONApi()
            ?.getArticle()
            ?.enqueue(object : Callback<AllArticles?> {
                override fun onFailure(call: Call<AllArticles?>, t: Throwable) {
                    Toast.makeText(context, t.message, id).show()
                }

                override fun onResponse(
                    call: Call<AllArticles?>,
                    response: Response<AllArticles?>
                ) {
                    val request = response.body()
                    when (request?.success) {
                        true -> {
                            articleItems1 = request.articles as List<Article>
                            //if (articleItems.role)
                            myAdapter =
                                RecyclerAdapter(articleItems1, object : RecyclerAdapter.Callback {
                                    override fun onItemClicked(item: Article) {
                                        val intent = Intent(
                                            context
                                            , ArticleActivity::class.java
                                        )
                                        intent.putExtra("author", item.author)
                                        intent.putExtra("date", item.date)
                                        intent.putExtra("title", item.title)
                                        intent.putExtra("message", item.messgae)
                                        startActivity(intent)
                                    }

                                })
                            recycler_view_orders.adapter = myAdapter
                        }
                    }
                }
            })
    }
}
