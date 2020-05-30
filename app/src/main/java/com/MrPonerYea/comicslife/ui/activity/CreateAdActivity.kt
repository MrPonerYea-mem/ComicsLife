package com.MrPonerYea.comicslife.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.singelton.SharedPrefManager
import kotlinx.android.synthetic.main.activity_create_ad.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setContentView(R.layout.activity_create_ad)

        if (intent.getBooleanExtra("edit", false)) {
            edit_text_price_ad.setText(intent.getStringExtra("price"))
            edit_text_address_ad_ad.setText(intent.getStringExtra("address"))
            edit_text_description_ad_ad.setText(intent.getStringExtra("description"))
            edit_text_phone_ad.setText(intent.getStringExtra("phone"))
            edit_text_title_ad.setText(intent.getStringExtra("title"))
            button_set_comics_ad.setText("Обновить")

        }

        button_set_comics_ad.setOnClickListener {
            if (!edit_text_description_ad_ad.text.isNullOrBlank() && !edit_text_phone_ad.text.isNullOrBlank() &&
                !edit_text_price_ad.text.isNullOrBlank() && !edit_text_title_ad.text.isNullOrBlank() &&
                !edit_text_address_ad_ad.text.isNullOrBlank()
            ) {
                if (intent.getBooleanExtra("edit", false)) {
                    NetworkService.getInstance()
                        ?.getJSONApi()
                        ?.updateComics(
                            edit_text_title_ad.text.toString(),
                            edit_text_description_ad_ad.text.toString(),
                            edit_text_phone_ad.text.toString(),
                            edit_text_address_ad_ad.text.toString(),
                            edit_text_price_ad.text.toString(),
                            intent.getIntExtra("idComics", 0)
                        )
                        ?.enqueue(object : Callback<GetLogin?> {
                            override fun onFailure(call: Call<GetLogin?>, t: Throwable) {
                                Toast.makeText(
                                    applicationContext,
                                    "Не удалось выполнить запрос",
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
                                            applicationContext,
                                            "Объявление успешно обнавлено",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    }
                                    false -> {
                                        Toast.makeText(
                                            applicationContext,
                                            reqest.message + "222",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Не удалось выполнить запрос",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        })
                } else {
                    NetworkService.getInstance()
                        ?.getJSONApi()
                        ?.setComics(
                            edit_text_title_ad.text.toString(),
                            edit_text_description_ad_ad.text.toString(),
                            edit_text_phone_ad.text.toString(),
                            SharedPrefManager.getLogin(applicationContext),
                            edit_text_address_ad_ad.text.toString(),
                            edit_text_price_ad.text.toString()
                        )
                        ?.enqueue(object : Callback<GetLogin?> {
                            override fun onFailure(call: Call<GetLogin?>, t: Throwable) {
                                Toast.makeText(
                                    applicationContext,
                                    "Не удалось подключится с серверу",
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
                                            applicationContext,
                                            "Объявление успешно создано",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    }
                                    false -> {
                                        Toast.makeText(
                                            applicationContext,
                                            reqest.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else -> {
                                        Toast.makeText(
                                            applicationContext,
                                            "Не удалось выполнить запрос",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                            }
                        })
                }
            } else {
                Toast.makeText(applicationContext, "Введите все данные", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item){
//
//        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
