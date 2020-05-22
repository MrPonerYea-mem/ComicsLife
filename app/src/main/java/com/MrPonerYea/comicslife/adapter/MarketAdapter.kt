package com.MrPonerYea.comicslife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.pojo.ItemMarket


class MarketAdapter(var cards: List<ItemMarket>) : RecyclerView.Adapter<MarketAdapter.CardViewHolder>() {

    class CardViewHolder internal constructor(var cardView: CardView) : RecyclerView.ViewHolder(
        cardView
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cv = LayoutInflater.from(parent.context) .inflate(R.layout.item_market, parent, false) as CardView
        return CardViewHolder(cv)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardView: CardView = holder.cardView
        val title = cardView.findViewById<View>(R.id.title) as TextView
        title.setText(cards[position].title)
        val content = cardView.findViewById<View>(R.id.content) as TextView
        content.setText(cards[position].date)
    }
}