package com.MrPonerYea.comicslife.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.adapter.MarketAdapter
import com.MrPonerYea.comicslife.data.pojo.ItemMarket
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val llm = LinearLayoutManager(context)
        val llm = GridLayoutManager(context, 2)
        recycler_view_market.setLayoutManager(llm)
        var cards = ArrayList<ItemMarket>()
        var dd = ItemMarket();
        dd.date= "1"
        dd.description = "2"
        dd.name = "3"
        dd.phone = "4"
        cards.add(dd)
        cards.add(dd)
        cards.add(dd)
        cards.add(dd)
        cards.add(dd)
        cards.add(dd)
        cards.add(dd)
        val dsds = MarketAdapter(cards)
        recycler_view_market.setAdapter(dsds)
    }
}
