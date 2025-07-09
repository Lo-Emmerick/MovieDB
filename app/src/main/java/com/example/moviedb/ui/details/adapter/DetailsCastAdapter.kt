package com.example.moviedb.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moviedb.databinding.ItemCastDetailsBinding
import com.example.moviedb.model.Cast

class DetailsCastAdapter (
    private val items: List<Cast>,

): Adapter<DetailsCastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsCastViewHolder {
        val binding = ItemCastDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailsCastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: DetailsCastViewHolder, position: Int) {
        holder.bind(items[position])
    }
}