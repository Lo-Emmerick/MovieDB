package com.example.moviedb.ui.listDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moviedb.databinding.ItemCastDetailsBinding
import com.example.moviedb.model.Cast

class ListDetailsCastAdapter (
    private val items: List<Cast>
): Adapter<ListDetailsCastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailsCastViewHolder {
        val binding =
            ItemCastDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDetailsCastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ListDetailsCastViewHolder, position: Int) {
        holder.bind(items[position])
    }
}