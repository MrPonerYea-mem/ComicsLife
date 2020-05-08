package com.MrPonerYea.comicslife.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.MrPonerYea.comicslife.MainActivity
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.singelton.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Стартовая активиту, при запуске проверяет если есть данные в системе и они достоверные, авторизует пользователя, иначе загружает форму с авторизацией
 */
class LoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        val handler: Handler = Handler()
        handler.postDelayed(Runnable {
            val user = SharedPrefManager.getUser(this)
            if (!user.email_sp.isNullOrBlank() && !user.password_sp.isNullOrBlank()) {
                val data: HashMap<String, String> = HashMap()
                data["email"] = user.email_sp.toString()
                data["password"] = user.password_sp.toString()
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
                            val log = response.body()
                            when (log?.success) {
                                true -> {
                                    val intent =
                                        Intent(applicationContext, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else -> {
                                    val intent =
                                        Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                    })
            }
        }, 1500)

    }
}
