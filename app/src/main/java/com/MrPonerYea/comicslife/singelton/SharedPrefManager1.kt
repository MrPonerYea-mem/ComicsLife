package com.MrPonerYea.comicslife.singelton

import android.content.Context

class SharedPrefManager1 private constructor() {
    companion object{
        private const val SHARED_PREF_NAME = "simplifiedcodingsharedpref"
        private const val KEY_USERNAME = "keyusername"
        private const val KEY_EMAIL = "keyemail"
        private const val KEY_GENDER = "keygender"
        private const val KEY_ID = "keyid"

        private val mInstance: SharedPrefManager1? = null
        private val mCtx: Context? = null
    }




}