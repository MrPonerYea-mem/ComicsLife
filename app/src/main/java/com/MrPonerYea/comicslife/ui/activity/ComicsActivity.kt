package com.MrPonerYea.comicslife.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.MrPonerYea.comicslife.R
import kotlinx.android.synthetic.main.activity_comics.*


class ComicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)

        text_view_title_comics.text = intent.getStringExtra("title")
        text_view_description_comics.text = intent.getStringExtra("description")
        text_view_date_comics.text = intent.getStringExtra("date")
        text_view_login_comics.text = intent.getStringExtra("login")
        text_view_adress_comics.text = intent.getStringExtra("adress")
        text_view_price_comics.text = intent.getStringExtra("price")

        button_call_comics.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + intent.getStringExtra("phone")))
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startActivity(intent)
            }
        })
    }
}
