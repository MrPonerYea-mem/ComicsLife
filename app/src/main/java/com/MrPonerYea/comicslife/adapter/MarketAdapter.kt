package com.MrPonerYea.comicslife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.pojo.Comics


class MarketAdapter(var cards: List<Comics>, listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<MarketAdapter.CardViewHolder>() {
    var mListener: RecyclerViewClickListener? = null

    init {
        mListener = listener
    }

    inner class CardViewHolder internal constructor(
        var cardView: CardView,
        listener: RecyclerViewClickListener?
    ) :
        RecyclerView.ViewHolder(
            cardView
        ), View.OnClickListener {
        var currentCardPosition = 0
        var mListener: RecyclerViewClickListener? = null

        init {
            mListener = listener
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mListener?.onClick(v, adapterPosition, cards[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cv = LayoutInflater.from(parent.context).inflate(
            R.layout.item_market,
            parent,
            false
        ) as CardView
        return CardViewHolder(cv, mListener)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardView: CardView = holder.cardView
        val title = cardView.findViewById<View>(R.id.text_view_title_item_market) as TextView
        title.setText(cards[position].title)

        val login = cardView.findViewById<View>(R.id.text_view_login_item_market) as TextView
        login.setText(cards[position].login)

        val date = cardView.findViewById<View>(R.id.text_view_date_item_market) as TextView
        date.setText(cards[position].date)
        holder.currentCardPosition = position
    }

    interface RecyclerViewClickListener {
        fun onClick(view: View?, position: Int, cards: Comics)
    }
}