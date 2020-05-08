package com.MrPonerYea.comicslife.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.data.pojo.User
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        button_registration.setOnClickListener {
            if (!edit_text_email.text.isNullOrBlank() && !edit_text_login.text.isNullOrBlank() && !edit_text_password.text.isNullOrBlank() && !edit_text_accept_password.text.isNullOrBlank()) {
                if (edit_text_password.text.toString() == edit_text_accept_password.text.toString()) {

                    NetworkService.getInstance()
                        ?.getJSONApi()
                        ?.registration(edit_text_email.text.toString(), edit_text_login.text.toString(), edit_text_password.text.toString())
                        ?.enqueue(object : Callback<GetLogin?> {
                            override fun onFailure(call: Call<GetLogin?>, t: Throwable) {
                                Toast.makeText(
                                    applicationContext,
                                    t.message,
                                    changingConfigurations
                                ).show()
                            }

                            override fun onResponse(
                                call: Call<GetLogin?>,
                                response: Response<GetLogin?>
                            ) {
                                val request = response.body()
                                when (request?.success) {
                                    true -> {
                                        val intent =
                                            Intent(applicationContext, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    false -> Toast.makeText(
                                        applicationContext,
                                        request.message,
                                        changingConfigurations
                                    ).show()
                                    else -> Toast.makeText(
                                        applicationContext,
                                        "Не удалось подключится к серверу, попробуйте позже",
                                        changingConfigurations
                                    ).show()
                                }
                            }
                        })
                } else {
                    Toast.makeText(this, "Пароли не совпадают", changingConfigurations).show()
                }
            } else {
                Toast.makeText(this, "Заполните все данные", changingConfigurations).show()
            }
        }

        text_view_to_sign_in.setOnClickListener {
            finish()
        }
    }
}
