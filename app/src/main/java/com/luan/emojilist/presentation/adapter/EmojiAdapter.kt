package com.luan.emojilist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.luan.common.domain.Emoji
import com.luan.common.extension.setImage
import com.luan.emojilist.R


class EmojiAdapter(private val listener: (Emoji) -> Unit) :
    RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder>() {


    var items : MutableList<Emoji> = arrayListOf()
        set (value) {
            field = value
            notifyDataSetChanged()
        }


    fun removeAt(item:Emoji){
        items.remove(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_emoji_list, parent, false)
        return EmojiViewHolder(
            view,
            listener)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    class EmojiViewHolder(itemView: View, private val listener: (Emoji) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val rootView = itemView.findViewById<CardView>(R.id.root)

        fun bind(item: Emoji) {
           image.setImage(item.source)
            rootView.setOnClickListener { listener.invoke(item) }
        }

    }
}