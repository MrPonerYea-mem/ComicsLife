package com.MrPonerYea.comicslife.singelton

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.MrPonerYea.comicslife.data.pojo.User

object SharedPrefManager {
    private const val SHARED_PREF_NAME = "simplifiedcodingsharedpref"
    private const val KEY_LOGIN = "keyusername"
    private const val KEY_EMAIL = "keyemail"
    private const val KEY_PASSWORD = "keypassword"

    @Synchronized
    fun saveUser(context:Context, user: User) {
        val pref: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_LOGIN, user.login)
        editor.putString(KEY_PASSWORD, user.password)
        editor.apply()
    }

    fun clearUser(context: Context) {
        val pref: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        pref.edit().clear().apply()
    }

    fun getUser(context: Context) : User {
        val pref: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        return User(pref.getString(KEY_LOGIN, "-1"), pref.getString(KEY_EMAIL, "-1"), pref.getString(KEY_PASSWORD, "-1"))
    }

}