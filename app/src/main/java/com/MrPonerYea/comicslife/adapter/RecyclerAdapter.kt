package com.MrPonerYea.comicslife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.MrPonerYea.comicslife.R
import com.MrPonerYea.comicslife.data.pojo.Article
import com.MrPonerYea.comicslife.data.pojo.ItemRecycler


class RecyclerAdapter(var items: List<Article>, val callback: Callback) :
    RecyclerView.Adapter<RecyclerAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
        MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_of_recycler_view,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return items.size
    }

    //передаем данные и оповещаем адаптер о необходимости обновления списка
    fun refreshUsers(article: List<Article>) {
        this.items = article
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val firstName = itemView.findViewById<TextView>(R.id.text_view_title)
        private val lastName = itemView.findViewById<TextView>(R.id.text_view_date)
        private val t = itemView.findViewById<TextView>(R.id.text_view_author)

        fun bind(item: Article) {
            firstName.text = item.title
            lastName.text = item.date
            t.text = item.author
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }

    }

    interface Callback {
        fun onItemClicked(item: Article)

    }

}

