package com.MrPonerYea.comicslife.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.adapter.MarketAdapter
import com.MrPonerYea.comicslife.adapter.RecyclerAdapter
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.*
import com.MrPonerYea.comicslife.singelton.SharedPrefManager
import com.MrPonerYea.comicslife.ui.activity.CreateAdActivity
import com.MrPonerYea.comicslife.ui.activity.CreateArticleActivity
import com.MrPonerYea.comicslife.utilities.CustomScrollView
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var myAdapter: RecyclerAdapter
    private lateinit var test: CustomScrollView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        getArticle(SharedPrefManager.getLogin(requireContext()))
        getUserComics()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        test1.isEnableScrolling = false
        //test = test1
        //test.isEnableScrolling = false // testtttttttttttttttt
        val user = SharedPrefManager.getUser(requireContext())
        edit_text_email_profile.setText(user.email_sp)
        edit_text_password_profile.setText(user.password_sp)
        text_view_login_profile.setText(user.login_sp)

        var flag = true
        button_open_article_profile.setOnClickListener(View.OnClickListener {
            if (flag) {
                val params: ViewGroup.LayoutParams = frame_layout_article_profile.getLayoutParams()
                params.height = FrameLayout.LayoutParams.WRAP_CONTENT
                frame_layout_article_profile.layoutParams = params
                flag = false
            } else {
                val params: ViewGroup.LayoutParams = frame_layout_article_profile.getLayoutParams()
                params.height = 720
                frame_layout_article_profile.layoutParams = params
                flag = true
            }
            //params.width = LayoutParams.WRAP_CONTENT
        })

        button_open_comics_profile.setOnClickListener(View.OnClickListener {
            if (flag) {
                val params: ViewGroup.LayoutParams = frame_layout_comics_profile.getLayoutParams()
                params.height = FrameLayout.LayoutParams.WRAP_CONTENT
                frame_layout_comics_profile.layoutParams = params
                flag = false
            } else {
                val params: ViewGroup.LayoutParams = frame_layout_comics_profile.getLayoutParams()
                params.height = 720
                frame_layout_comics_profile.layoutParams = params
                flag = true
            }
        })

        recycler_view_comics_profile.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == 1) {
                    //test.isEnableScrolling = false
                    //Toast.makeText(requireContext(), newState.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    //test1.isEnableScrolling = true
                }
                //super.onScrollStateChanged(recyclerView, newState)
            }
        })

        val login = SharedPrefManager.getLogin(requireContext())

        button_update_profile.setOnClickListener(View.OnClickListener {
            if (!edit_text_email_profile.text.isNullOrBlank() && !edit_text_password_profile.text.isNullOrBlank()) {
                NetworkService.getInstance()
                    ?.getJSONApi()
                    ?.updateProfile(
                        login,
                        edit_text_email_profile.text.toString(),
                        edit_text_password_profile.text.toString()
                    )
                    ?.enqueue(object : Callback<GetLogin?> {
                        override fun onFailure(call: Call<GetLogin?>, t: Throwable) {
                            Toast.makeText(
                                requireContext(),
                                "Не удалось подключится к серверу",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<GetLogin?>,
                            response: Response<GetLogin?>
                        ) {
                            val reqest = response.body()
                            when (reqest?.success) {
                                true -> {
                                    Toast.makeText(
                                        context,
                                        "Профиль успешно изменен",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    SharedPrefManager.clearUser(requireContext())
                                    val user1: User = User("", "", "")
                                    user1.login = text_view_login_profile.text.toString()
                                    user1.email = edit_text_email_profile.text.toString()
                                    user1.password = edit_text_password_profile.text.toString()

                                    SharedPrefManager.saveUser(
                                        requireContext(),
                                        user1
                                    )
                                }
                                false -> {
                                    Toast.makeText(context, reqest.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Не удалось выполнить запрос к серверу",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
            }
        })

        getArticle(login)
        getUserComics()
    }

    private fun getUserComics() {
        val llm = GridLayoutManager(requireContext(), 1)
        recycler_view_comics_profile.setLayoutManager(llm)
        NetworkService.getInstance()
            ?.getJSONApi()
            ?.getUserComics(SharedPrefManager.getLogin(requireContext()))
            ?.enqueue(object : Callback<ItemMarket?> {
                override fun onFailure(call: Call<ItemMarket?>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось выполнить запрос",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<ItemMarket?>, response: Response<ItemMarket?>) {
                    val reqest = response.body()
                    when (reqest?.success) {
                        true -> {
                            if (!reqest.comics.isNullOrEmpty()) {
                                val adapter = MarketAdapter(
                                    reqest.comics,
                                    object : MarketAdapter.RecyclerViewClickListener {
                                        override fun onClick(
                                            view: View?,
                                            position: Int,
                                            cards: Comics
                                        ) {
                                            val intent =
                                                Intent(context, CreateAdActivity::class.java)
                                            intent.putExtra("edit", true)
                                            intent.putExtra("address", cards.adress)
                                            intent.putExtra("phone", cards.phone)
                                            intent.putExtra("date", cards.date)
                                            intent.putExtra("description", cards.description)
                                            intent.putExtra("title", cards.title)
                                            intent.putExtra("idComics", cards.idComics)
                                            intent.putExtra("price", cards.price)
                                            startActivity(intent)
                                        }
                                    })
                                recycler_view_comics_profile.setAdapter(adapter) //adapter(adapter)
                            }
                        }
                    }
                }
            })
    }

    private fun getArticle(login: String) {
        NetworkService.getInstance()
            ?.getJSONApi()
            ?.getUserArticle(login)
            ?.enqueue(object : Callback<AllArticles?> {
                override fun onFailure(call: Call<AllArticles?>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось подключится к серверу " + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<AllArticles?>,
                    response: Response<AllArticles?>
                ) {
                    val reqest = response.body()
                    when (reqest?.success) {
                        true -> {
                            val articles = reqest.articles as List<Article>
                            myAdapter =
                                RecyclerAdapter(articles, object : RecyclerAdapter.Callback {
                                    override fun onItemClicked(item: Article) {
                                        val intent = Intent(
                                            context
                                            , CreateArticleActivity::class.java
                                        )
                                        intent.putExtra("edit", true)
                                        intent.putExtra("id", item.idArticle)
                                        intent.putExtra("author", item.author)
                                        intent.putExtra("date", item.date)
                                        intent.putExtra("title", item.title)
                                        intent.putExtra("message", item.messgae)
                                        startActivity(intent)
                                    }
                                })
                            recycler_view_article_profile.adapter = myAdapter
                        }
                        false -> {
                            Toast.makeText(requireContext(), reqest.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "Не удалось выполнить запрос",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }
}
