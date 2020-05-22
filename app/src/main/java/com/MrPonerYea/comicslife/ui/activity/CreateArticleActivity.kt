package com.MrPonerYea.comicslife.ui.activity

import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.singelton.SharedPrefManager
import kotlinx.android.synthetic.main.activity_create_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_article)

        button_add_title.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<h2> </h2>")
        }

        button_add_b.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<b> </b>")
        }

        button__add_img.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<img src=\"http://\" alt=\"image\" width=100%/>");
        }

        button_add_blockquite.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<blockquote> </blockquote>");
        }

        button_add_ol.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<ol>\n" + "\t<li> </li>\n" + "</ol>");
        }

        button_add_ref.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<p> </p>>");
        }

        button_add_u.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<u> </u>");
        }

        button_add_ul.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<ul>\n" + "\t<li> </li>\n" + "</ul>");
        }

        button_add_br.setOnClickListener {
            edit_text_maintext.text.insert(edit_text_maintext.selectionStart, "<br>");
        }

        button_clear_text.setOnClickListener {
            edit_text_maintext.setText("")
            edit_text_title.setText("")
        }

        button_create_article.setOnClickListener {
            if (!edit_text_maintext.text.isNullOrBlank() && !edit_text_title.text.isNullOrBlank() && SharedPrefManager.getLogin(applicationContext) != "-1") {
                NetworkService.getInstance()
                    ?.getJSONApi()
                    ?.addArticle(SharedPrefManager.getLogin(applicationContext), edit_text_title.text.toString(), edit_text_maintext.text.toString())
                    ?.enqueue(object : Callback<GetLogin?> {
                        override fun onFailure(call: Call<GetLogin?>, t: Throwable) {
                            Toast.makeText(applicationContext, "Не удалось подлючится к серверу" + t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<GetLogin?>,
                            response: Response<GetLogin?>
                        ) {
                            val request = response.body()
                            when(request?.success){
                                true-> {
                                    Toast.makeText(applicationContext, "Статья успешно добавлена", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                false ->{
                                    Toast.makeText(applicationContext, request.message, Toast.LENGTH_SHORT).show()
                                }
                                else ->{
                                    Toast.makeText(applicationContext, "Не удалось подлючится к серверу, попробуйте позже", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
            } else {
                Toast.makeText(applicationContext, "Введите текст во все поля", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
