package com.MrPonerYea.comicslife

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.MrPonerYea.comicslife.adapter.RecyclerAdapter
import com.MrPonerYea.comicslife.data.network.NetworkService
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.data.pojo.ItemRecycler
import com.MrPonerYea.comicslife.data.pojo.User
import com.MrPonerYea.comicslife.singelton.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val items = listOf(
            ItemRecycler("Александр", "Пушкин", "2"),
            ItemRecycler("Михаил", "Лермонтов", "2"),
            ItemRecycler("Александр", "Блок", "2"),
            ItemRecycler("Николай", "Некрасов", "2"),
            ItemRecycler("Фёдор", "Тютчев", "2"),
            ItemRecycler("Сергей", "Есенин", "2"),
            ItemRecycler("Владимир", "Маяковский", "2")
        )

        val myAdapter = RecyclerAdapter(items, object : RecyclerAdapter.Callback {
            override fun onItemClicked(item: ItemRecycler) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        recycler_view_orders.adapter = myAdapter
    }
}
