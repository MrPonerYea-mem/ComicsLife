package com.MrPonerYea.comicslife.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.adapter.MarketAdapter
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.Comics
import com.MrPonerYea.comicslife.data.pojo.ItemMarket
import com.MrPonerYea.comicslife.ui.activity.ComicsActivity
import com.MrPonerYea.comicslife.ui.activity.CreateAdActivity
import com.MrPonerYea.comicslife.ui.activity.CreateArticleActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var listener: MarketAdapter.RecyclerViewClickListener
    private lateinit var itemM: List<Comics>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        setComicsInAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val llm = LinearLayoutManager(context)
        val llm = GridLayoutManager(context, 2)
        recycler_view_market.setLayoutManager(llm)
        setComicsInAdapter()

        floating_action_button_market.setOnClickListener {
            startActivity(Intent(context, CreateAdActivity::class.java))
        }

        handler = Handler()
        swipe_refresh_layout_market.setOnRefreshListener {
            runnable = Runnable {
                setComicsInAdapter()
                swipe_refresh_layout_market.isRefreshing = false
            }

            handler.postDelayed(runnable, 1500.toLong())
        }

        swipe_refresh_layout_market.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light
        )

        listener = object : MarketAdapter.RecyclerViewClickListener {
            override fun onClick(view: View?, position: Int, cards: Comics) {
                val intent = Intent(context, ComicsActivity::class.java)
                intent.putExtra("title", cards.title)
                intent.putExtra("description", cards.description)
                intent.putExtra("login", cards.login)
                intent.putExtra("date", cards.date)
                intent.putExtra("phone", cards.phone)
                intent.putExtra("adress", cards.adress)
                intent.putExtra("price", cards.price)
                startActivity(intent)
            }
        }
    }

    fun setComicsInAdapter() {
        NetworkService.getInstance()
            ?.getJSONApi()
            ?.getComics()
            ?.enqueue(object : Callback<ItemMarket?> {
                override fun onFailure(call: Call<ItemMarket?>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ItemMarket?>, response: Response<ItemMarket?>) {
                    val request = response.body()
                    when (request?.success){
                        true -> {
                            if (!request.comics.isNullOrEmpty()) {
                                itemM = request.comics
                                val comics = MarketAdapter(request.comics, listener)
                                recycler_view_market.setAdapter(comics)
                            }
                        }
                        false -> {
                            Toast.makeText(context, request.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "Не удалось выполнить завпрос", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }
}
