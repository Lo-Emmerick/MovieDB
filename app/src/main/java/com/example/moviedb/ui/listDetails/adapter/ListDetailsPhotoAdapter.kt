package com.example.moviedb.ui.listDetails.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moviedb.databinding.ItemPhotoDetailsBinding
import com.example.moviedb.databinding.ItemPhotoListDetailsBinding
import com.example.moviedb.model.Photo

class ListDetailsPhotoAdapter (
    private val items: List<Photo>
): Adapter<ListDetailsPhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailsPhotoViewHolder {
        val binding =
            ItemPhotoListDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDetailsPhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ListDetailsPhotoViewHolder, position: Int) {
        holder.bind(items[position])
    }
}