package com.luan.avatarlist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.domain.Emoji
import com.luan.common.extension.setImage
import com.luan.avatarlist.R


class AvatarAdapter(private val listener: (GitUser) -> Unit) :
    RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {


    var items: MutableList<GitUser> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_avatar_list, parent, false)
        return AvatarViewHolder(
            view,
            listener
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    class AvatarViewHolder(itemView: View, private val listener: (GitUser) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val rootView = itemView.findViewById<CardView>(R.id.root)

        fun bind(item: GitUser) {
            image.setImage(item.avatarUrl)
            rootView.setOnClickListener { listener.invoke(item) }
        }

    }
}