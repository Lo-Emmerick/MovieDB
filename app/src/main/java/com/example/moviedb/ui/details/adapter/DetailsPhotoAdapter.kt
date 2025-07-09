package com.example.moviedb.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moviedb.databinding.ItemPhotoDetailsBinding
import com.example.moviedb.model.Photo

class DetailsPhotoAdapter  (
    private val items: List<Photo>
): Adapter<DetailsPhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsPhotoViewHolder {
        val binding =
            ItemPhotoDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailsPhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: DetailsPhotoViewHolder, position: Int) {
        holder.bind(items[position])
    }
}