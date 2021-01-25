package com.luan.repolist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.luan.repolist.R
import com.luan.repolist.domain.model.Repository


class RepositoriesAdapter() :
    RecyclerView.Adapter<RepositoriesAdapter.AvatarViewHolder>() {

    var items: MutableList<Repository> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_repositories, parent, false)
        return AvatarViewHolder(view)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    class AvatarViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.name)
        private val private = itemView.findViewById<ConstraintLayout>(R.id.isPrivate)

        fun bind(item: Repository) {
            title.text = item.fullName
            private.visibility = if (item.private) View.VISIBLE else View.GONE
        }

    }
}