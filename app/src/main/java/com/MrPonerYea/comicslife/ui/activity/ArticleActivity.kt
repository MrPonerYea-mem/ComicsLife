package com.MrPonerYea.comicslife.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.MrPonerYea.comicslife.R
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        text_view_author.text = intent.getStringExtra("author")
        text_view_title.text = intent.getStringExtra("title")
        text_view_message.text = intent.getStringExtra("message")
        text_view_date.text = intent.getStringExtra("date")

        val message = intent.getStringExtra("message") ?: null;
        if (message != null) {
            web_view_text.loadDataWithBaseURL(null, message, "text/html", "utf-8", null)
        } else {
            web_view_text.loadDataWithBaseURL(null, "<h2>Текста нет</h2>", "text/html", "utf-8", null)
        }
    }


}
