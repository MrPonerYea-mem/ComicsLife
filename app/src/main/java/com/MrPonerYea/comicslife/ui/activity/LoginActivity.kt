package com.MrPonerYea.comicslife.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.MrPonerYea.comicslife.MainActivity
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.data.pojo.User
import com.MrPonerYea.comicslife.singelton.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        button_sign_in.setOnClickListener {
            if (!edit_text_email.text.isNullOrBlank() && !edit_text_password.text.isNullOrBlank()) {
                val data: MutableMap<String, String> = HashMap()
                data["email"] = edit_text_email.text.toString()
                data["password"] = edit_text_password.text.toString()

                NetworkService.getInstance()
                    ?.getJSONApi()
                    ?.getLoginWithEmailandPassword(data)
                    ?.enqueue(object : Callback<GetLogin?> {
                        override fun onFailure(call: Call<GetLogin?>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, changingConfigurations)
                                .show()
                        }

                        override fun onResponse(
                            call: Call<GetLogin?>,
                            response: Response<GetLogin?>
                        ) {
                            val request = response.body()
                            when (request?.success) {
                                true -> {
                                    val user: User? = request.user
                                    if (user != null) {
                                        SharedPrefManager.saveUser(applicationContext, user)
                                        val intent =
                                            Intent(applicationContext, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                                false -> {
                                    Toast.makeText(
                                        applicationContext,
                                        request.message,
                                        changingConfigurations
                                    ).show()
                                }
                                else -> Toast.makeText(
                                    applicationContext,
                                    "Не удалось подключится к серверу, попробуйте позже",
                                    changingConfigurations
                                ).show()
                            }
                        }
                    })
            } else {
                Toast.makeText(this, "Введите почту и пароль", changingConfigurations).show()
            }

        }

        text_view_to_registration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
